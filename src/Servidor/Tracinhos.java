package Servidor;

import java.io.Serializable;
import java.util.Arrays;

public class Tracinhos implements Cloneable, Serializable
{
    private char[] texto;

    public Tracinhos (int qtd) throws Exception
    {
        if(qtd<=0)
            throw new Exception("Quantidade menor que 0");

        this.texto = new char[qtd];
        for (int i=0;i<qtd;i++){
            this.texto[i] = '_';
        }
    }

    public void revele (int posicao, char letra) throws Exception
    {
        if(posicao<0 || posicao >= this.texto.length)
            throw new Exception("posicao oferece um valor fora do vetor");

        this.texto[posicao] = letra;
    }

    public boolean isAindaComTracinhos ()
    {
        boolean achou = false;
        for(int i=0;i<this.texto.length;i++){
            if(this.texto[i] == '_'){
                achou = true;
                break;
            }
        }
        return achou;
    }

    @Override
    public String toString ()
    {
        StringBuilder strBuilder = new StringBuilder("");
        for (char c : this.texto) {
            strBuilder.append(c).append(" ");
        }
        strBuilder.append(")");
        return strBuilder.toString();
    }

    @Override
    public boolean equals (Object obj)
    {
        if(this == obj)  return true;
        if (obj == null) return false;

        if(obj.getClass()!=Tracinhos.class) return false;
        Tracinhos tracinhoss = (Tracinhos) obj;

        return Arrays.equals(this.texto, tracinhoss.texto);
    }

    @Override
    public int hashCode ()
    {
        int ret = 999;
        ret = 7*ret + Arrays.hashCode(this.texto);
        if (ret < 0)
            ret =- ret;

        return ret;
    }

    public Tracinhos (Tracinhos t) throws Exception // construtor de cÃģpia
    {
        this.texto = new char[t.texto.length];
        this.texto = t.texto;
    }

    @Override
    public Object clone ()
    {
        Tracinhos y = null;
        try{
            y = new Tracinhos(this);
        }
        catch(Exception erro){}

        return y;
    }
}
