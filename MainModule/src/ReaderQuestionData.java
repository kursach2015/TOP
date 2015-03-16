import java.io.*;
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
class Question{                     //готовый вопрос
    String question;
    String[] answers;
    FormatSettings formatSettings=new FormatSettings();
    public void print(){
        System.out.println(question);
        switch (formatSettings.getType()) {
            case COLUMN: {
                for (int i = 0; i < answers.length; i++)
                    if (i==answers.length-1)
                        System.out.print("(_) " + answers[i]);
                    else
                        System.out.println("(_) " + answers[i]);
                break;
            }
            case COLUMNS: {
                int i;
                for (i = 1; i < answers.length+1; i++) {
                    System.out.print("(_) " + answers[i-1]+"\t");
                    if (i%formatSettings.getCount()==0 && i!=answers.length)
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
                int i;
                for (i = 1; i < answers.length+1; i++) {
                    System.out.print("(_) " + answers[i-1]+"\t");
                    if (i%k==0 && i!=answers.length)
                    {
                        System.out.println();
                    }
                }
                break;
            }
        }
        System.out.println();

    }
    public String getQuestion(){
        return question;
    }
    public String getAnswers(){
        String res="";
        switch (formatSettings.getType()) {
            case COLUMN: {
                for (int i = 0; i < answers.length; i++)
                    if (i==answers.length-1)
                        res+="(_) " + answers[i];
                    else
                        res+="(_) " + answers[i]+"\r\n";
                break;
            }
            case COLUMNS: {
                int i;
                for (i = 1; i < answers.length+1; i++) {
                    res+="(_) " + answers[i-1]+"\t";
                    if (i%formatSettings.getCount()==0 && i!=answers.length)
                    {
                        res+="\r\n";
                    }

                }
                break;
            }
            case ROW: {
                for (int i = 0; i < answers.length; i++)
                    res+="(_) " + answers[i] + "\t";
                break;
            }
            case ROWS: {
                int k= answers.length/formatSettings.getCount();
                int i;
                for (i = 1; i < answers.length+1; i++) {
                    res+="(_) " + answers[i-1]+"\t";
                    if (i%k==0 && i!=answers.length)
                    {
                        res+="\r\n";
                    }
                }
                break;
            }
        }
        res+="\r\n";
        return res;
    }
}
class QuestionList{                     //список вопросов. класс недоработан
    List<Question> questionsList;
    QuestionList(ArrayList<Question> arrayList){
        questionsList=arrayList;
    }
    QuestionList(){
        questionsList=new ArrayList<Question>();
    }
    public ArrayList<Question> getQuestionList(){
        return new ArrayList<Question>(questionsList);
    }
    public void add_question(Question question){
        questionsList.add(question);
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
                    f_pos = in.next();
                    f_neg = in.next();
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
class Variant                // готовый вариант
{
    int number;
    QuestionList questionList;
    Variant(int num,QuestionList questionList)
    {
        this.number=num;
        this.questionList=questionList;
    }
    Variant(int num)
    {
        this.number=num;
        questionList= new QuestionList();
    }
    public void addQuestion(Question question)
    {
        questionList.add_question(question);
    }
    public void print_to_console()     //вывод варианта в консоль
    {
        ArrayList<Question> questions=questionList.getQuestionList();
        for(Question q :questions)
        {
            q.print();
        }
    }
    public void print_to_file(String fname) throws IOException {    //запись варианта в файл
        FileWriter fileWriter=new FileWriter(new File(fname));
        StringWriter stringWriter=new StringWriter();
        CharSequence charSequence="";
        for (Question question:questionList.getQuestionList()){
            charSequence= charSequence+question.getQuestion() + "\r\n" + question.getAnswers();
        }
        fileWriter.append(charSequence);
        stringWriter.close();
        fileWriter.close();
    }
}