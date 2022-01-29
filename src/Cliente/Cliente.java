package Cliente;

import java.net.Socket;

public class Cliente {
    // caso o cliente não quiser digitar o host/ip, ele vai automaticamente para localhost
    public static final String HOST_PADRAO  = "localhost";
    public static void main(String[] args)
    {
        String host = null;

        // preferimos deixar os usuários digitarem a porta para evitar erros
        String porta = null;
        try
        {
            System.out.println("Digite o Host/IP: ");
            host = Teclado.getUmString();
            System.out.println("Digite a porta que quer se conectar: ");
            porta = Teclado.getUmString();
            System.out.println("Aguarde o jogo já ira começar!!!");
        }
        catch (Exception erro)
        {
            System.err.println ("Algum dos valores passados não esta correto!\n");
            return;
        }

        if (host == null)
        {
            host = HOST_PADRAO;
        }

        Socket conexao = null;
        try
        {
                conexao = new Socket(host, Integer.parseInt(porta));
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        SupervisoraCliente supervisoraCliente = null;
        try
        {
            supervisoraCliente = new SupervisoraCliente(conexao);
            supervisoraCliente.start();
        }
        catch (Exception erro)
        {
            System.err.println ("conexão falha!\n");
            return;
        }
    }
}
