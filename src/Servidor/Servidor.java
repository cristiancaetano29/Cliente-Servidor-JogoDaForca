package Servidor;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor
{
    public static void main (String[] args) throws IOException {

        // preferimos deixar os usuários digitarem a porta para evitar erros
        System.out.print("Indique a porta desejada: ");
        String porta = null;
        try
        {
            porta = Teclado.getUmString();
        }
        catch(Exception e)
        {
            System.err.println("Valor invalido");
            return;
        }

        ArrayList<Socket> jogadores = new ArrayList<Socket>();
        AceitadoraDeConexao aceitadoraDeConexao = null;
        try
        {
            aceitadoraDeConexao = new AceitadoraDeConexao(porta, jogadores);
            aceitadoraDeConexao.start();
        }
        catch (Exception erro)
        {
            System.err.println ("Escolha uma porta apropriada e liberada para uso!\n");
        }

         for (;;)
        {
            System.out.println("O servidor esta ativo! Para desativa-lo, use o comando \"desativar\"\n");
            System.out.print("> ");

            String comando = null;

            try
            {
                comando = Teclado.getUmString();
            }
            catch (Exception erro)
            { }

            if (comando.toLowerCase().equals("desativar"))
            {
                System.out.println ("O servidor foi desativado!\n");
                System.exit(0);
            }
            else
                System.err.println ("Comando invalido!\n");
        }
    }
}
