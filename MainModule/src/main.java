/**
 * Created by just1ce on 15.03.2015.
 */
import java.io.*;
public class main {

    public static void main(String[] args) {
        QuestionParser questionParser=new QuestionParser("quest_1.txt");
        try {
            QuestionGenerator questionGenerator = questionParser.make_readed_question();
            //System.out.println("Readed question:");
           // questionGenerator.print();
            Question question = questionGenerator.make_question();
            //question.print();
            Variant variant1=new Variant(1);
            variant1.addQuestion(question);
            variant1.Print_to_console();

        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch (WrongDataQuestEx e)
        {
            System.out.println(e.getMessage());
        }
    }
}
