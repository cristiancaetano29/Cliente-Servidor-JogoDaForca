package Servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class SupervisoraDeConexao extends Thread
{
    private ArrayList<Socket> jogadoresDaSala;


    public SupervisoraDeConexao (ArrayList<Socket> jogadoresDaSala) throws Exception
    {
       this.jogadoresDaSala = jogadoresDaSala;
    }

    public void notificarJogadores(String message) throws IOException
    {
        for (Socket user : jogadoresDaSala)
        {
           if (!user.isClosed()){this.notificarJogador(user, message);}
        }
    }

    public void notificarJogador(Socket socket, String message) throws IOException {
        ObjectOutputStream socketOutput = new ObjectOutputStream(socket.getOutputStream());
        socketOutput.writeObject(message);
    }

    @Override
    public void run()
    {
        try
        {

            boolean gameEnded = false;
            Palavra palavra = BancoDePalavras.getPalavraSorteada();
            Tracinhos tracinhos = null;
            try
            {
                tracinhos = new Tracinhos (palavra.getTamanho());
            }
            catch (Exception erro)
            {}

            ControladorDeLetrasJaDigitadas
                    controladorDeLetrasJaDigitadas =
                    new ControladorDeLetrasJaDigitadas ();

            int qtdJogadores =3;
            while (!gameEnded) {
                try {
                    if (this.jogadoresDaSala.size() > 0) {
                        for (Socket user : this.jogadoresDaSala) {
                            if (!user.isClosed()) {

                                if (qtdJogadores == 1) {
                                    this.notificarJogador(user, "a palavra era:" + " " + palavra.toString());
                                    notificarJogador(user, MensagensServidor.Ganhou);
                                    gameEnded = true;
                                    System.out.println("um jogo acabou");
                                    currentThread().stop();
                                }
                                MensagensServidor.palavraString = tracinhos.toString();

                                this.notificarJogadores("palavra....:" + MensagensServidor.palavraString);
                                this.notificarJogador(user, MensagensServidor.DigiteUmChar);
                                char opcao = receberCharDoCliente(user);

                                switch (opcao) {
                                    case 'P': {
                                        this.notificarJogador(user, MensagensServidor.ChutePalavra);
                                        String chutePalavra = receberStringDoCliente(user);
                                        if (chutePalavra.equals(palavra.toString())) {
                                            notificarJogadores("a palavra era:" + " " + palavra.toString());
                                            this.notificarJogador(user, MensagensServidor.Ganhou);
                                            notificarJogadores(MensagensServidor.PerdeuPara);
                                            gameEnded = true;
                                            System.out.println("um jogo acabou");
                                            currentThread().stop();
                                        } else {
                                            this.notificarJogador(user, "a palavra era:" + " " + palavra.toString());
                                            this.notificarJogador(user, MensagensServidor.PerdeuPalavra);
                                            user.close();
                                            qtdJogadores--;
                                        }
                                        break;
                                    }
                                    case 'L': {
                                        char letra = ' ';

                                        this.notificarJogador(user, MensagensServidor.ChuteLetra);
                                        letra = receberCharDoCliente(user);
                                        System.out.println(letra);
                                        while (controladorDeLetrasJaDigitadas.isJaDigitada(letra)) {
                                            this.notificarJogador(user, MensagensServidor.ChuteLetra);
                                            letra = receberCharDoCliente(user);
                                        }
                                        System.out.println(letra);
                                        controladorDeLetrasJaDigitadas.registre(letra);
                                        int qtd = palavra.getQuantidade(letra);
                                        if (qtd == 0) {
                                            this.notificarJogador(user, "a palavra era:" + " " + palavra.toString());
                                            notificarJogador(user, MensagensServidor.PerdeuLetra);
                                            user.close();
                                            qtdJogadores--;

                                        } else {
                                            for (int i = 0; i < qtd; i++) {

                                                int posicao = palavra.getPosicaoDaIezimaOcorrencia(i, letra);
                                                tracinhos.revele(posicao, letra);
                                            }
                                            System.out.println(tracinhos.toString());
                                        }
                                        break;
                                    }
                                    case 'S': {
                                        this.notificarJogador(user, MensagensServidor.SairDoJogo);
                                        user.close();
                                        qtdJogadores--;
                                        break;
                                    }
                                }
                                if (!tracinhos.isAindaComTracinhos()) {
                                    this.notificarJogador(user, "a palavra era:" + " " + palavra.toString());
                                    this.notificarJogador(user, MensagensServidor.Ganhou);
                                    if (!user.isClosed()) {
                                        user.close();
                                        notificarJogadores("a palavra era:" + " " + palavra.toString());
                                        notificarJogadores(MensagensServidor.PerdeuPara);
                                    }
                                    gameEnded = true;
                                    System.out.println("um jogo acabou");
                                    currentThread().stop();
                                }
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
        catch(Exception erro)
        {}
    }

    private String receberStringDoCliente(Socket user) {
        boolean response = false;
        String result = "";

        while (!response) {
            try {
                Scanner userInput = new Scanner(user.getInputStream());
                result = userInput.nextLine();

                if (result.trim().length() > 0) response = true;
            } catch (Exception ex) {
            }
        }
        return result;
    }

    private char receberCharDoCliente(Socket user)
    {
        char result = ' ';
         try
         {
            Scanner sc = new Scanner(user.getInputStream());
            result = sc.next().charAt(0);
         }
         catch (Exception ex)
         {}
        return result;
    }
}