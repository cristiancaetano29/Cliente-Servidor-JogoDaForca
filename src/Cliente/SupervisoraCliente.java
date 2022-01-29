package Cliente;

import Servidor.MensagensServidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SupervisoraCliente extends Thread
{
    private Socket socket;

    public SupervisoraCliente(Socket socket) {
        this.socket = socket;
    }

    private void escreverParaOServidor(String message) throws IOException
    {
        PrintStream socketWriter = new PrintStream(socket.getOutputStream());
        socketWriter.println(message);
    }
    private void charParaOServidor(char message) throws IOException
    {
        PrintStream socketWriter = new PrintStream(socket.getOutputStream());
        socketWriter.println(message);
    }



    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                ObjectInputStream serverInput = new ObjectInputStream(socket.getInputStream());
                String message = (String)serverInput.readObject();

                if (message.length() > 0)
                {
                    System.out.println(message);

                    if (MensagensServidor.DigiteUmChar.equals(message))
                    {
                        boolean response = false;
                        char opcao = ' ';
                        while (!response)
                        {
                            try
                            {
                                Scanner sc = new Scanner(System.in);
                                opcao = sc.next().charAt(0);
                                opcao = Character.toUpperCase(opcao);
                                if ("PLS".indexOf(opcao) != -1) response = true;
                            } catch (Exception ex)
                            {
                            }
                        }
                        charParaOServidor(opcao);
                    }

                    if(MensagensServidor.ChutePalavra.equals(message))
                    {
                        boolean response = false;
                        String chutePalavra ="";
                        while (!response)
                        {
                            try {
                                Scanner sc = new Scanner(System.in);
                                chutePalavra = sc.nextLine();
                                if (chutePalavra.length()>0)response=true;
                            }catch (Exception ex)
                            {
                            }
                        }
                        escreverParaOServidor(chutePalavra);
                    }
                    if(MensagensServidor.PerdeuPalavra.equals(message) || MensagensServidor.PerdeuLetra.equals(message) || MensagensServidor.PerdeuPara.equals(message)
                            || MensagensServidor.Ganhou.equals(message) || MensagensServidor.SairDoJogo.equals(message))
                    {
                        socket.close();
                        System.exit(0);
                    }
                    if(MensagensServidor.ChuteLetra.equals(message))
                    {
                        boolean response = false;
                        char letra = ' ';
                        while (!response)
                        {
                            try
                            {
                                Scanner sc = new Scanner(System.in);
                                letra = sc.next().charAt(0);

                                if (letra != ' ') response = true;
                            } catch (Exception ex)
                            {
                            }
                        }
                        charParaOServidor(letra);
                    }

                }
            } catch (Exception ex)
            {
                if(socket.isConnected())
                {
                    try
                    {
                        System.out.println("O servidor esta sendo Desligado Volte mais tarde");
                        currentThread().stop();
                        System.exit(0);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
    }
}
