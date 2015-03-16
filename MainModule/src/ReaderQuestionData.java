import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class WrongDataQuestEx extends Exception{
    @Override
    public String getMessage() {
        return "Wrong data question.";
    }
};
enum format_type
{
    ROW,COLUMN,ROWS,COLUMNS
}
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
    FormatSettings formatSettings=new FormatSettings();
    public void print(){
        System.out.println(question);
        switch (formatSettings.getType()) {
            case COLUMN: {
                for (int i = 0; i < answers.length; i++)
                    System.out.println("(_) " + answers[i]);
                break;
            }
            case COLUMNS: {
                for (int i = 1; i < answers.length+1; i++) {
                    System.out.print("(_) " + answers[i-1]+"\t");
                    if (i%formatSettings.getCount()==0)
                    {
                        System.out.println();
                    }
                }
                break;
            }
            case ROW: {
                for (int i = 0; i < answers.length; i++)
                    System.out.print("(_) " + answers[i] + "\t");
                break;
            }
            case ROWS: {
                int k= answers.length/formatSettings.getCount();
                for (int i = 1; i < answers.length+1; i++) {
                    System.out.print("(_) " + answers[i-1]+"\t");
                    if (i%k==0)
                    {
                        System.out.println();
                    }
                }
                break;
            }
        }

    }
}
class QuestionGenerator{                //создает объект типа Question
    String preambula;
    int col_answers;
    int col_pos_answers;
    int col_neg_answers;
    String[] answ_pos;
    String[] answ_neg;
    FormatSettings formatSettings=new FormatSettings();

    public void print(){
        System.out.println(preambula);
        System.out.println(col_answers);
        System.out.println(col_pos_answers);
        System.out.println(col_neg_answers);
        System.out.println(Arrays.toString(answ_pos));
        System.out.println(Arrays.toString(answ_neg));
        System.out.println(formatSettings.getType());
        System.out.println(formatSettings.getCount());
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
        question.formatSettings=formatSettings;
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
class FormatSettings
{
    format_type type;
    int count;
    public void setType(format_type t){
        this.type=t;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public format_type getType(){
        return type;
    }
    public int getCount(){
        return count;
    }
}
class QuestionParser{               //считывает вопрос и ответы с файлов и заполняет структуру
    String fname;
    String preambula;
    FormatSettings formatSettings=new FormatSettings();
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
            try {
                    preambula = in.nextLine();
                    col_answers = in.nextInt();
                    col_pos_answers = in.nextInt();
                    col_neg_answers = in.nextInt();
                    String type=in.next();
                    if(type.equals("column"))
                    {
                        formatSettings.setType(format_type.COLUMN);
                        formatSettings.setCount(0);
                    }
                    else
                        if(type.equals("columns"))
                        {
                             formatSettings.setType(format_type.COLUMNS);
                            formatSettings.setCount(in.nextInt());
                        }
                        else
                        if(type.equals("row"))
                        {
                            formatSettings.setType(format_type.ROW);
                            formatSettings.setCount(0);
                        }
                        else
                        if(type.equals("rows"))
                        {
                            formatSettings.setType(format_type.ROWS);
                            formatSettings.setCount(in.nextInt());
                        }

                    f_pos = in.next();
                    f_neg = in.next();


            } catch (Exception e)
            {
                throw new WrongDataQuestEx();
            }

            AnswersReader answersReader=new AnswersReader(f_pos);
            answ_pos=answersReader.getAnswers();
            AnswersReader answersReader1=new AnswersReader(f_neg);
            answ_neg=answersReader1.getAnswers();
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
        que.formatSettings=formatSettings;
        return que;
    }
}
class Variant
{
    int number;
    List<Question> questionsList;
    Variant(int num,ArrayList<Question> q)
    {
        this.number=num;
        this.questionsList=q;
    }
    Variant(int num)
    {
        this.number=num;
        questionsList= new ArrayList<Question>();
    }
    public void addQuestion(Question q)
    {
        questionsList.add(q);
    }
    public void Print_to_console()
    {
        for(Question q :questionsList)
        {
            q.print();
        }
    }

}