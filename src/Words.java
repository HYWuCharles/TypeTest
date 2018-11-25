import java.io.*;
import java.util.Random;

/**
 * Created by ComingWind on 2018/11/19.
 */
public class Words {

    private int wordsNum = 993;

    private String[] wordsList = new String[wordsNum];

    private String fileName;

    public int maxNum = 100;

    private int letterNumber;

    public Words(String fileName){
        this.fileName = fileName;
    }

    public int getWordsNum() {
        return wordsNum;
    }

    public void setWordsNum(int wordsNum) {
        this.wordsNum = wordsNum;
    }

    public String[] generateWords(){
        String[] res = new String[maxNum];
        int i = 0;
        try{
            BufferedReader bfr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("words.txt")));
            String line = bfr.readLine();
            while(line != null){
                wordsList[i] = line;
                i++;
                line = bfr.readLine();
            }
            bfr.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        i = 0;
        Random random = new Random((int)System.currentTimeMillis()%wordsNum);
        while (i < maxNum){
            res[i] = wordsList[random.nextInt(wordsNum)];
            i++;
        }

        return res;
    }

    public void countLetters(String[] wordsList){
        int count = 0;
        for (int i = 0; i < maxNum; i++){
            count += wordsList[i].length();
        }
        setWordsNum(count);
    }

}
