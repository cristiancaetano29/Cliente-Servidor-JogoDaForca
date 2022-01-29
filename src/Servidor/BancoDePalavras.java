package Servidor;

public class BancoDePalavras
{
    private static String[] palavras =
            {
                    "amar",
                    "imprescindivel",
                    "responsabilidade",
                    "interdependencia",
                    "indubitavelmente",
                    "peremptoriamente",
                    "inconstitucional",
                    "sustentabilidade",
                    "subsidiariamente",
                    "termofisiologico",
                    "superprocessador",
                    "anticonstitucionalismo",
                    "pneumoultramicroscopicossilicovulcanoconiotico"
            };

    public static Palavra getPalavraSorteada ()
    {
        Palavra palavra = null;

        try
        {
            palavra =
                    new Palavra (BancoDePalavras.palavras[
                            (int)(Math.random() * BancoDePalavras.palavras.length)]);
        }
        catch (Exception e)
        {}

        return palavra;
    }

}
