package br.com.remoteready.model.response;

public class LoginResponse {
    private Long id;
    private String nome;
    private String email;
    private String role;
    private String token;

    public LoginResponse(Long id, String nome, String email, String role, String token) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
        this.token = token;
        ;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getToken() { return token; }
}
