package com.example.request_thegame.Helper;

public class TradutorCodigoMorse {



    public static String getCodigoMorse(String mensagemPortugues){


        char[] inputMensagem = mensagemPortugues.toLowerCase().toCharArray();

        String codigoMorse = " ";

        char[] portugues = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3',
                '4', '5', '6', '7', '8', '9', '0', ',', '.', '?','!', ':', '='};

        String[] morse = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
                ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
                "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
                "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
                "-----", "--..--", ".-.-.-", "..--..","..--.", "---...", "-...-" };

        for(int index=0; index<inputMensagem.length;index++){
            for(int i=0;i<portugues.length;i++){
                if(inputMensagem[index]==portugues[i]){
                    codigoMorse=codigoMorse+morse[i]+" ";
                }
            }
        }

        return codigoMorse;
    }
}
