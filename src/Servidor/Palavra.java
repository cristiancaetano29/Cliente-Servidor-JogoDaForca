package Servidor;
import java.util.ArrayList;

public class Palavra implements Comparable<Palavra>
{
    private String texto;
    private ArrayList<Integer> indicesLidos;

    public Palavra (String texto) throws Exception
    {
        if (texto == null)
            throw new Exception("erro!");

        this.texto = texto;
        this.indicesLidos = new ArrayList<Integer>();
    }

    public int getQuantidade (char letra)
    {
        int qtd =0;
        for (int i = 0; i < getTamanho(); i++)
        {
            if(this.texto.charAt(i) == letra)
                qtd++;
        }
        return qtd;
    }

    public int getPosicaoDaIezimaOcorrencia (int i, char letra) throws Exception
    {

        int posicao = this.texto.indexOf(letra,i);
        boolean posicaoJaLida = false;

        do
        {
            boolean lido = false;
            if(this.indicesLidos.indexOf(posicao) >= 0)
            {
                lido = true;
                i++;
                posicao = this.texto.indexOf(letra,i);
            }

            posicaoJaLida = lido;
        }
        while(posicaoJaLida);


        if(posicao == -1)
            throw new Exception("posicao errada!");

        this.indicesLidos.add(posicao);

        return posicao;
    }


    public int getTamanho ()
    {
        return this.texto.length();
    }

    @Override
    public String toString ()
    {
        return this.texto;
    }

    @Override
    public boolean equals (Object obj)
    {
        if(this == obj)  return true;
        if (obj == null) return false;

        if(obj.getClass()!=Palavra.class) return false;
        Palavra palavra = (Palavra) obj;

        return this.texto.equals(palavra.texto);
    }

    @Override
    public int hashCode ()
    {
        int ret = 999;
        ret = 7*ret + this.hashCode();

        return ret;
    }

    @Override
    public int compareTo (Palavra palavra)
    {
        return this.texto.compareTo(palavra.texto);
    }
}
