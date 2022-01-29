package Servidor;

public class ControladorDeLetrasJaDigitadas implements Cloneable
{
    private String letrasJaDigitadas;

    public ControladorDeLetrasJaDigitadas ()
    {
        this.letrasJaDigitadas = "";
    }

    public boolean isJaDigitada (char letra)
    {
        return this.letrasJaDigitadas.indexOf(letra) >= 0;
    }

    public void registre (char letra) throws Exception
    {
        if(this.isJaDigitada(letra))
            throw new Exception("Letra já digitada");

        this.letrasJaDigitadas += letra;
    }

    @Override
    public String toString ()
    {
        StringBuilder x = new StringBuilder();
        for(int i =0; i<this.letrasJaDigitadas.length() ;i++)
        {
            x.append(this.letrasJaDigitadas.charAt(i)).append(" ");
        }

        return x.toString();
    }

    @Override
    public boolean equals (Object obj)
    {
        if(this == obj)  return true;
        if (obj == null) return false;

        if(obj.getClass()!=ControladorDeLetrasJaDigitadas.class) return false;
        ControladorDeLetrasJaDigitadas controlador = (ControladorDeLetrasJaDigitadas) obj;

        return this.letrasJaDigitadas.equals(controlador.letrasJaDigitadas);
    }

    @Override
    public int hashCode ()
    {
        int ret = 999;
        ret = 7*ret + this.letrasJaDigitadas.hashCode();
        if (ret < 0)
            ret =- ret;
        return ret;
    }

    public ControladorDeLetrasJaDigitadas(ControladorDeLetrasJaDigitadas co) throws Exception
    // construtor de cópia
    {
        this.letrasJaDigitadas = co.letrasJaDigitadas;
    }

    @Override
    public Object clone ()
    {
        ControladorDeLetrasJaDigitadas y = null;
        try
        {
            y = new ControladorDeLetrasJaDigitadas(this);
        }
        catch(Exception erro){}

        return y;
    }
}
