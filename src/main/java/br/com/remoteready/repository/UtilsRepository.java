package br.com.remoteready.repository;

import br.com.remoteready.dto.ChatHistoryResult;
import br.com.remoteready.dto.ChatMensagemDTO;
import br.com.remoteready.dto.RelatorioElegibilidadeDTO;
import br.com.remoteready.dto.UsuarioAptoDTO;
import br.com.remoteready.dto.UsuarioNaoAptoDTO;
import jakarta.annotation.PostConstruct;
import org.hibernate.dialect.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UtilsRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall spHistoricoUsuario;
    private SimpleJdbcCall spRelatorioEngajamento;

    public UtilsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    void init() {
        DataSource dataSource = jdbcTemplate.getDataSource();
        if (dataSource == null) {
            throw new IllegalStateException("DataSource não configurado no JdbcTemplate");
        }

        // Procedure: PKG_REMOTEREADY.PRC_HISTORICO_USUARIO
        this.spHistoricoUsuario = new SimpleJdbcCall(dataSource)
                .withCatalogName("PKG_REMOTEREADY")           // nome do pacote
                .withProcedureName("PRC_HISTORICO_USUARIO")   // nome da procedure
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ID_USUARIO", Types.NUMERIC),
                        new SqlParameter("P_TIPO_HISTORICO", Types.VARCHAR),
                        new SqlOutParameter("P_USUARIO", OracleTypes.CURSOR, (rs, rowNum) -> mapUsuarioBasico(rs)),
                        new SqlOutParameter("P_CHATS", OracleTypes.CURSOR, (rs, rowNum) -> mapChat(rs))
                );

        // Procedure: PKG_REMOTEREADY.PRC_RELATORIO_ENGAJAMENTO
        this.spRelatorioEngajamento = new SimpleJdbcCall(dataSource)
                .withCatalogName("PKG_REMOTEREADY")
                .withProcedureName("PRC_RELATORIO_ENGAJAMENTO")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_DIAS", Types.NUMERIC),
                        new SqlOutParameter("P_APTOS", OracleTypes.CURSOR, (rs, rowNum) -> mapUsuarioApto(rs)),
                        new SqlOutParameter("P_NAO_APTOS", OracleTypes.CURSOR, (rs, rowNum) -> mapUsuarioNaoApto(rs))
                );
    }

    // ---------- HISTÓRICO DE CHAT (procedure PRC_HISTORICO_USUARIO) ----------

    public Optional<ChatHistoryResult> obterHistoricoUsuario(Long idUsuario) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_ID_USUARIO", idUsuario)
                .addValue("P_TIPO_HISTORICO", "CHAT");

        Map<String, Object> out = spHistoricoUsuario.execute(params);

        @SuppressWarnings("unchecked")
        List<ChatHistoryResult> usuarios = (List<ChatHistoryResult>) out.get("P_USUARIO");
        if (usuarios == null || usuarios.isEmpty()) {
            return Optional.empty();
        }

        ChatHistoryResult result = usuarios.get(0);

        @SuppressWarnings("unchecked")
        List<ChatMensagemDTO> chats = (List<ChatMensagemDTO>) out.get("P_CHATS");
        result.setMensagens(chats);

        return Optional.of(result);
    }

    private ChatHistoryResult mapUsuarioBasico(ResultSet rs) throws SQLException {
        ChatHistoryResult dto = new ChatHistoryResult();
        dto.setIdUsuario(rs.getLong("ID_USUARIO"));
        dto.setNome(rs.getString("NM_USUARIO"));
        dto.setEmail(rs.getString("DS_EMAIL"));
        return dto;
    }

    private ChatMensagemDTO mapChat(ResultSet rs) throws SQLException {
        ChatMensagemDTO dto = new ChatMensagemDTO();
        dto.setIdChat(rs.getLong("ID_CHAT"));

        LocalDateTime data = rs.getTimestamp("DT_CRIACAO").toLocalDateTime();
        dto.setData(data);

        dto.setPergunta(rs.getString("PROMPT_SHORT"));
        dto.setResposta(rs.getString("RESPONSE_SHORT"));

        return dto;
    }

    // ---------- RELATÓRIO DE ELEGIBILIDADE (procedure PRC_RELATORIO_ENGAJAMENTO) ----------

    public RelatorioElegibilidadeDTO gerarRelatorioElegibilidade(int dias) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_DIAS", dias);

        Map<String, Object> out = spRelatorioEngajamento.execute(params);

        @SuppressWarnings("unchecked")
        List<UsuarioAptoDTO> aptos = (List<UsuarioAptoDTO>) out.get("P_APTOS");

        @SuppressWarnings("unchecked")
        List<UsuarioNaoAptoDTO> naoAptos = (List<UsuarioNaoAptoDTO>) out.get("P_NAO_APTOS");

        RelatorioElegibilidadeDTO dto = new RelatorioElegibilidadeDTO();
        LocalDate hoje = LocalDate.now();
        dto.setDataFim(hoje);
        dto.setDataInicio(hoje.minusDays(dias));
        dto.setUsuariosAptos(aptos);
        dto.setUsuariosNaoAptos(naoAptos);

        return dto;
    }

    private UsuarioAptoDTO mapUsuarioApto(ResultSet rs) throws SQLException {
        UsuarioAptoDTO dto = new UsuarioAptoDTO();
        dto.setIdUsuario(rs.getLong("ID_USUARIO"));
        dto.setNome(rs.getString("NM_USUARIO"));
        dto.setEmail(rs.getString("DS_EMAIL"));
        dto.setPostsLidos(rs.getLong("QT_LIDOS"));
        dto.setStatusCertificado(rs.getString("STATUS_CERT"));
        return dto;
    }

    private UsuarioNaoAptoDTO mapUsuarioNaoApto(ResultSet rs) throws SQLException {
        UsuarioNaoAptoDTO dto = new UsuarioNaoAptoDTO();
        dto.setIdUsuario(rs.getLong("ID_USUARIO"));
        dto.setNome(rs.getString("NM_USUARIO"));
        dto.setPostsLidos(rs.getLong("QT_LIDOS"));
        dto.setFaltam(rs.getLong("FALTAM"));
        return dto;
    }
}
