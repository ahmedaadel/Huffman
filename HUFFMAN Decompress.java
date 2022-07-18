import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.CodeSigner;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.standard.Copies;

public class Decompress {

    public static void main(String[] args) throws IOException {

        ArrayList<Character> Symbol = new ArrayList<Character>();

        ArrayList<String> Code = new ArrayList<String>();
        File file = new File("Dictionary.txt");
        // System.out.print((char) content);

        try (FileReader fr = new FileReader(file)) {
            int content;
            String temp="";
            while ((content = fr.read()) != -1) {
                if(Character.isLetter((char)content)||Character.isWhitespace((char)content))
                    {
                        Symbol.add((char)content);
                    }
                else if((char)content==':')
                    continue;
                else if((char)content==',')
                    {
                        Code.add(temp);
                        temp="";
                    }
                else
                    temp=temp+(char)content;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // -----------------------------------------ReadCompressCode-----------------------------------------------------------
        File CompressFile = new File(
                "D:\\eng_marwan\\FifthTerm\\Information Theory and Data Compression\\DecompressHuffman\\output.txt");
        Scanner sc = new Scanner(CompressFile);
        String CompressCode = "";
        while (sc.hasNextLine())
            CompressCode = sc.nextLine() + CompressCode;
        // --------------------------------------------------------------------------------------------------------------------
        String originalText = "";
        String CheckerCode = "";
        int i = 0;

        while (i <= CompressCode.length()) {
            if (i < CompressCode.length())
                CheckerCode = CheckerCode + CompressCode.charAt(i);
            i++;
            for (int j = 0; j < Code.size(); j++) {
                if (CheckerCode.equals(Code.get(j))) {
                    originalText = originalText + Symbol.get(j);
                    CheckerCode = "";
                }
            }
        }
        FileWriter myWriter = new FileWriter("OriginalText.txt");
        myWriter.write(originalText);
        myWriter.close();

        System.out.println(originalText);
    }

}
