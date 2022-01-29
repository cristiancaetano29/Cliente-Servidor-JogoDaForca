package Servidor;

import java.net.*;
import java.util.*;

public class AceitadoraDeConexao extends Thread
{
    private ServerSocket        serverSocket;
    private Queue<Socket> filaDeJogadores;

    public AceitadoraDeConexao (String porta, ArrayList<Socket> filaDeJogadores) throws Exception
    {
        if (porta==null)
            throw new Exception ("Porta ausente");
        try
        {
            this.serverSocket = new ServerSocket (Integer.parseInt(porta));
        }
        catch (Exception  erro)
        {
            throw new Exception ("Porta invalida");
        }
        if (filaDeJogadores==null)
            throw new Exception ("Jogadores ausentes");

        this.filaDeJogadores = new LinkedList<Socket>();
    }

    @Override
    public void run()
    {
        for(;;)
        {
            try
            {
                Socket socket = this.serverSocket.accept();
                filaDeJogadores.add(socket);
                System.out.println("adicionou o socket");
                tryToCreateRoom();
            }
            catch(Exception e)
            {}
        }
    }

    private void tryToCreateRoom() throws Exception
    {
        if (this.filaDeJogadores.size() % 3 == 0)
        {
            while (this.filaDeJogadores.size() > 0)
            {
                ArrayList<Socket> jogadoresDaSala = new ArrayList<Socket>();
                for (int i = 1; i <= 3; i++)
                {
                    Socket socket = this.filaDeJogadores.poll();
                    jogadoresDaSala.add(socket);
                }
                SupervisoraDeConexao sala = new SupervisoraDeConexao(jogadoresDaSala);
                System.out.println(jogadoresDaSala.toString());
                sala.notificarJogadores("Bem vindo");
                sala.start();
            }
        }
    }
}