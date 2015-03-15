/**
 * Created by just1ce on 15.03.2015.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

enum ReadState{OK,     //ok
               FNF,    //question file not found
               RE,     //read error
               DE,     //input data error

};

class Question{
    String question;
    String[] answers;
    public void print(){
        System.out.println(question);
        for (int i=0;i<answers.length;i++)
            System.out.println("-"+answers[i]);
        //System.out.println(Arrays.toString(answers));
    }
}
class ReadedQuestion{
    String preambula;
    int col_answers;
    int col_pos_answers;
    int col_neg_answers;
    String[] answ_pos;
    String[] answ_neg;
    boolean isok;

    public void print(){
        System.out.println(isok);
        System.out.println(preambula);
        System.out.println(col_answers);
        System.out.println(col_pos_answers);
        System.out.println(col_neg_answers);
        System.out.println(Arrays.toString(answ_pos));
        System.out.println(Arrays.toString(answ_neg));
    }
    private static void swap(String[] a, int i, int change) {
        String temp = a[i];
        a[i] = a[change];
        a[change] = temp;
    }
    public void shuffleArray(String[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }
    public Question make_question(){
        Question question=new Question();
        question.question=preambula;
        question.answers=new String[col_answers];
        shuffleArray(answ_pos);
        shuffleArray(answ_neg);
        int i;
        for (i=0;i<col_pos_answers;i++)
            question.answers[i]=answ_pos[i];
        for (int j=0;j<col_neg_answers;j++)
            question.answers[i+j]=answ_neg[j];
        shuffleArray(question.answers);
        return question;
    }
};
class QuestionReader{
    String fname;
    String preambula;
    int col_answers;
    int col_pos_answers;
    int col_neg_answers;
    String[] answ_pos;
    String[] answ_neg;

    QuestionReader(String filename){
        fname=filename;
    }
    private String[] read_answers(Scanner in){
        int len=0;
        if (in.hasNextInt())
            len=in.nextInt();
        String[] res=new String[len];
        int i=0;
        while (in.hasNext() &&  (i<len)){
            res[i++]=in.next();
        }
//        System.out.println(Arrays.toString(res));
        return res;

    }
    private ReadState read_question(){
        String f_pos="", f_neg="";
        try {
            Scanner in = new Scanner(new File(fname));
            if (in.hasNext())
                preambula=in.nextLine();
            else return ReadState.RE;
            if (in.hasNextInt())
                col_answers=in.nextInt();
            else return ReadState.RE;
            if (in.hasNextInt())
                col_pos_answers=in.nextInt();
            else return ReadState.RE;
            if (in.hasNextInt())
                col_neg_answers=in.nextInt();
            else return ReadState.RE;
            if (in.hasNext()){
                 f_pos=in.next();}
            else return ReadState.RE;
            if (in.hasNext())
                f_neg=in.next();
            else return ReadState.RE;
//            System.out.println(preambula+" "+col_answers+" "+col_pos_answers+" "+col_neg_answers+" "+f_pos+" "+f_neg);
            Scanner answpos = new Scanner(new File(f_pos));
            answ_pos=read_answers(answpos);
            Scanner answneg = new Scanner(new File(f_neg));
            answ_neg=read_answers(answneg);
//            System.out.println(Arrays.toString(answ_pos));
//            System.out.println(Arrays.toString(answ_neg));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            return ReadState.FNF;
        }
        return ReadState.OK;
    }
    public ReadedQuestion make_readed_question(){
        ReadedQuestion que=new ReadedQuestion();
        if (read_question()==ReadState.OK)
        {
            que.preambula=preambula;
            que.col_answers=col_answers;
            que.col_pos_answers=col_pos_answers;
            que.col_neg_answers=col_neg_answers;
            que.answ_pos=answ_pos;
            que.answ_neg=answ_neg;
            que.isok=true;
        }
        else
            que.isok=false;
        return que;
    }
}

public class main {

    public static void main(String[] args) {
        QuestionReader questionReader=new QuestionReader("C:\\Users\\storo_000\\Documents\\GitHub\\TOP\\MainModule\\src\\quest_1.txt");
        ReadedQuestion readedQuestion=questionReader.make_readed_question();
        //System.out.println("Readed question:");
        //readedQuestion.print();
        Question question=readedQuestion.make_question();
        System.out.println("Question:");
        question.print();
    }



}
