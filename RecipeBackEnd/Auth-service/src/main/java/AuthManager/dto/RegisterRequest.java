package AuthManager.dto;


import lombok.*;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String msg;
    public RegisterRequest(String msg){
        this.msg=msg;
    }
}
