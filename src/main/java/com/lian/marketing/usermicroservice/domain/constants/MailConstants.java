package com.lian.marketing.usermicroservice.domain.constants;

public class MailConstants {
    private MailConstants() {}

    public static final String SUBJECT_VERIFICATION_MAIL = "Code verification";
    public static final String CONTENT_VERIFICATION_MAIL_HTML = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Verificación de Cuenta</title>
                <style>
                    .container {
                        width: 80%%;
                        margin: auto;
                        padding: 20px;
                        border: 1px solid #ddd;
                        border-radius: 10px;
                        text-align: center;
                        font-family: Arial, sans-serif;
                    }
                    h1 {
                        color: #3498db;
                    }
                    p {
                        font-size: 18px;
                        color: #555;
                    }
                    .code {
                        font-size: 24px;
                        font-weight: bold;
                        color: #e74c3c;
                        background: #f8f9fa;
                        display: inline-block;
                        padding: 10px;
                        border-radius: 5px;
                        margin-top: 10px;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 14px;
                        color: #777;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>¡Verifica tu Cuenta!</h1>
                    <p>Hola, nos alegra que quieras unirte a nosotros.</p>
                    <p>Para completar tu registro, usa el siguiente código de verificación:</p>
                    <div class="code"> %s </div>
                    <p>Este código expirará en 5 minutos.</p>
                    <p>Si no solicitaste este código, puedes ignorar este correo.</p>
                    <p class="footer">Gracias por confiar en nosotros.</p>
                </div>
            </body>
            </html>
            """;
}
