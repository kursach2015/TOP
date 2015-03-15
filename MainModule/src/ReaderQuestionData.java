import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class WrongDataQuestEx extends Exception{
    @Override
    public String getMessage() {
        return "Wrong data question.";
    }
};
class AnswersReader{            //читает ответы в массив строк с файла
    String fname;
    String[] answers;
    AnswersReader(String filename)throws FileNotFoundException{
        fname=filename;
        read_answers();
    }
    private void read_answers() throws FileNotFoundException{
        int len=0;
        Scanner in = new Scanner(new File(fname));
        if (in.hasNextInt())
            len=in.nextInt();
        answers=new String[len];
        int i=0;
        while (in.hasNext() &&  (i<len)){
            answers[i++]=in.next();
        }
//        System.out.println(Arrays.toString(res));
        in.close();
    }
    public String[] getAnswers(){
        return answers;
    }
}
class Question{                     //готовый вопрос, который идет на выход
    String question;
    String[] answers;
    public void print(){
        System.out.println(question);
        for (int i = 0; i < answers.length; i++)
            System.out.println("\t- " + answers[i]);
    }
}
class QuestionGenerator{                //создает объект типа Question
    String preambula;
    int col_answers;
    int col_pos_answers;
    int col_neg_answers;
    String[] answ_pos;
    String[] answ_neg;

    public void print(){
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
    private void shuffleArray(String[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }
    public Question make_question() throws WrongDataQuestEx{
        Question question=new Question();
        question.question=preambula;
        if (col_answers!=col_neg_answers+col_pos_answers)
            throw new WrongDataQuestEx();
        question.answers=new String[col_answers];
        shuffleArray(answ_pos);
        shuffleArray(answ_neg);
        int i = 0, j = 0;
        while (i < col_pos_answers && j < answ_pos.length) {
            if (answ_pos[j] != null) {
                question.answers[i++] = answ_pos[j++];

            } else
                j++;
        }
        if (i == col_pos_answers) {
            j = 0;
            while (i < col_answers && j < answ_neg.length) {
                if (answ_neg[j] != null) {
                    question.answers[i++] = answ_neg[j++];

                } else
                    j++;
            }
            if (i != col_answers)
                throw new WrongDataQuestEx();
            shuffleArray(question.answers);
        } else
            throw  new WrongDataQuestEx();
        return question;
    }
};
class QuestionParser{               //считывает вопрос и ответы с файлов и заполняет структуру
    String fname;
    String preambula;
    int col_answers;
    int col_pos_answers;
    int col_neg_answers;
    String[] answ_pos;
    String[] answ_neg;

    QuestionParser(String filename){
        fname=filename;
    }

    private void read_question() throws FileNotFoundException, WrongDataQuestEx{
        String f_pos="", f_neg="";
        try {
            Scanner in = new Scanner(new File(fname));
            if (in.hasNext())
                preambula = in.nextLine();
            else throw new WrongDataQuestEx();
            if (in.hasNextInt())
                col_answers = in.nextInt();
            else throw new WrongDataQuestEx();
            if (in.hasNextInt())
                col_pos_answers = in.nextInt();
            else throw new WrongDataQuestEx();
            if (in.hasNextInt())
                col_neg_answers = in.nextInt();
            else throw new WrongDataQuestEx();
            if (in.hasNext()) {
                f_pos = in.next();
            } else throw new WrongDataQuestEx();
            if (in.hasNext())
                f_neg = in.next();
            else throw new WrongDataQuestEx();
//            System.out.println(preambula+" "+col_answers+" "+col_pos_answers+" "+col_neg_answers+" "+f_pos+" "+f_neg);
            AnswersReader answersReader=new AnswersReader(f_pos);
            answ_pos=answersReader.getAnswers();
            AnswersReader answersReader1=new AnswersReader(f_neg);
            answ_neg=answersReader1.getAnswers();
//            System.out.println(Arrays.toString(answ_pos));
//            System.out.println(Arrays.toString(answ_neg));
        }
        catch (FileNotFoundException e)
        {
            throw  new FileNotFoundException("File not found. Plz check path to file.");
        }
    }
    public QuestionGenerator make_readed_question() throws FileNotFoundException, WrongDataQuestEx{
        QuestionGenerator que=new QuestionGenerator();
        read_question();
        que.preambula=preambula;
        que.col_answers=col_answers;
        que.col_pos_answers=col_pos_answers;
        que.col_neg_answers=col_neg_answers;
        que.answ_pos=answ_pos;
        que.answ_neg=answ_neg;
        return que;
    }
}
