/**
 * Created by just1ce on 15.03.2015.
 */
import java.io.*;
public class main {

    public static void main(String[] args) {
        String path="";

        QuestionList questionList=new QuestionList();
        for (int i = 1; i < 11; i++) {
            path="quest_"+String.valueOf(i)+".txt";
            QuestionParser questionParser = new QuestionParser(path);
            try {
                QuestionGenerator questionGenerator = questionParser.make_readed_question();
                Question question = questionGenerator.make_question();
                questionList.add_question(question);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println(e.getMessage()+"№"+i);
            } catch (WrongDataQuestEx e) {
                e.printStackTrace();
                System.out.println(e.getMessage()+"№"+i);
            }
        }
        Variant variant1 = new Variant(1,questionList);
        variant1.print_to_console();
        String out="output1.txt";
        try {
            variant1.print_to_file(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
