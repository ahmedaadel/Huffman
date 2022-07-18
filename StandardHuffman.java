package standard.huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Node {

    int fr;
    char Symbol;
    Node left = null, right = null;

    Node(char Symbol, int fr) {
        this.Symbol = Symbol;
        this.fr = fr;
    }

    Node(int fr, Node left, Node right) {

        this.fr = fr;
        this.right = right;
        this.left = left;
    }

}

class Huffman {

    Map<Character, String> Huffcode = new HashMap<>();

    public void encode(Node rot, String s) {
        if (rot.left == null && rot.right == null) {
            Huffcode.put(rot.Symbol, s);
            return;
        }

        encode(rot.left, s + "0");
        encode(rot.right, s + "1");

    }

    public Map<Character, String> getHuffCode() {
        return Huffcode;
    }
}

public class StandardHuffman {

    public static void main(String[] args) throws IOException {
        Map<Character, Integer> freq = new HashMap<>();

        String data = "", out = "";
        String line;
        int charctr=0;

        //Reading from file
        File obj = new File("input.txt");
        try {

            FileInputStream fileInputStream = new FileInputStream(obj);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            try {
                while ((line = bufferedReader.readLine()) != null) {

                    charctr+=line.length();
                    data += line;
                }

            } catch (IOException ex) {
                System.out.println("Error occuered");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error occuered");
        }

        for (char ch : data.toCharArray()) {
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }

        PriorityQueue<Node> pq;
        pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.fr));

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        Node root = null;

        while (pq.size() != 1) {

            Node left = pq.poll();
            Node right = pq.poll();
            int sum = left.fr + right.fr;

            root = new Node(sum, left, right);
            pq.add(root);

        }

        Huffman f = new Huffman();
        f.encode(root, "");

        Map<Character, String> code;
        code = f.getHuffCode();
        System.out.println(f.getHuffCode());

        for (int i = 0; i < data.length(); i++) {
            out += code.get(data.charAt(i));
        }
        System.out.println(out);

        String Directory = System.getProperty("user.dir");
        String Fname = Directory + "/output.txt";
        FileWriter output = new FileWriter(Fname);
        output.write(out);
        output.close();
        
        FileWriter Dic = new FileWriter("Dictionary.txt");
       for (Map.Entry<Character,String> entry : code.entrySet()) {
           Dic.write(entry.getKey());
           Dic.write(":"); 
           Dic.write(entry.getValue());
           Dic.write("  ");
           
            
        }
        Dic.close();
        
        System.out.println("num of characters is "+charctr);
        
        int alphctr,compratio=0;
        for (char ch : data.toCharArray()) {
            alphctr=freq.get(ch);
            String s=code.get(ch);
            compratio+=alphctr*s.length();
        }
            
        
        
        
        System.out.println(compratio);
    }

}
