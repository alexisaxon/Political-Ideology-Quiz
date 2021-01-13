import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.net.*;


public class Quiz extends JFrame implements ActionListener {
    private ButtonGroup responses;
    private ButtonGroup numOptions;
    private JButton arrow;
    private JRadioButton strongAgree;
    private JRadioButton agree;
    private JRadioButton disagree;
    private JRadioButton strongDisagree;
    private JLabel L1;
    private JRadioButton one;
    private JRadioButton two;
    private JRadioButton three;
    private JRadioButton four;
    private JLabel L3;

    private double currentSoc;
    private double currentEcon;
    private double socWeight;
    private double econWeight;
    private int index;
    private String[] questions;
    private boolean[] social;
    private boolean[] left;

    private boolean isSocial;
    private boolean agreeIsLeft;
    private int numEconQ;
    private int numSocQ;


    public Quiz(String title, String question) {
        super(title);
        questions = new String[16];
        social = new boolean[16];
        left = new boolean[16];
        currentSoc = 0;
        currentEcon = 0;
        socWeight = 0;
        econWeight = 0;
        numEconQ=0;
        numSocQ=0;
        index=-1;
        questions[0]="It is more important for a government to meet the needs of its people than to maintain a balanced budget";
        questions[1] = "The United States should interfere less in foreign affairs and focus more on issues at home";
        questions[2]="Most people in the United States can get ahead if they work hard";
        questions[3]="Wealthy Americans are taxed too much by the federal government";
        questions[4]="Women no longer face more obstacles than men in achieving success";
        questions[5]="In order to be moral, one must be religious";
        questions[6]="The primary purpose of education is to prepare students for future employment";
        questions[7] = "Convicts currently in prison should have the right to vote";
        questions[8] = "Protectionism (tariffs) is sometimes necessary in international trade";
        questions[9] = "The primary responsibility of companies is to deliver a profit to their shareholders";
        questions[10] = "Abortion should be illegal in all cases except when the life of the mother is threatened";
        questions[11] = "The institution of a Universal Basic Income is fundamentally a good idea";
        questions[12] = "No world culture is more civilized than any other";
        questions[13] = "Recreational marijuana should be legalized";
        questions[14] = "There are only two genders";
        questions[15] = "The minimum wage should be substantially increased";
        social[0]=false;
        social[1]=true;
        social[2]=false;
        social[3]=false;
        social[4]=true;
        social[5]=true;
        social[6] = true;
        social[7] = true;
        social[8] = false;
        social[9]=false;
        social[10]=true;
        social[11] =false;
        social[12]=true;
        social[13]=true;
        social[14]=true;
        social[15]=false;
        left[0]=true;
        left[1]=true;
        left[2]=false;
        left[3]=false;
        left[4]=false;
        left[5]=false;
        left[6]=false;
        left[7]=true;
        left[8]=true;
        left[9]=false;
        left[10]=false;
        left[11]=true;
        left[12]=true;
        left[13]=true;
        left[14]=false;
        left[15]=true;

        L1 = new JLabel(question);
        JPanel pnResponses = new JPanel();
        JPanel everything = new JPanel();
        everything.setAlignmentX(Component.LEFT_ALIGNMENT);
        everything.setLayout(new BoxLayout(everything,BoxLayout.Y_AXIS));
        pnResponses.setLayout(new BoxLayout(pnResponses,BoxLayout.Y_AXIS));
        responses = new ButtonGroup();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        strongAgree = new JRadioButton("Strongly agree", false);
        agree = new JRadioButton("Agree", false);
        disagree = new JRadioButton("Disagree",false);
        strongDisagree = new JRadioButton("Strongly disagree",false);
        responses.add(strongAgree);
        responses.add(agree);
        responses.add(disagree);
        responses.add(strongDisagree);
        BoxLayout bl = new BoxLayout(pnResponses, BoxLayout.Y_AXIS);
        pnResponses.setLayout(bl);
        pnResponses.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnResponses.add(L1);
        pnResponses.add(strongAgree);
        pnResponses.add(agree);
        pnResponses.add(disagree);
        pnResponses.add(strongDisagree);
        everything.add(pnResponses);

        setResizable(false);

        //setting up the weighting options
        JLabel L2 = new JLabel("What is the relative importance of this issue to you? 1 means of little importance and 4 is of high importance.");
        numOptions = new ButtonGroup();
        one = new JRadioButton("1");
        two = new JRadioButton("2");
        three = new JRadioButton("3");
        four = new JRadioButton("4");
        JPanel pnNums = new JPanel();
        pnNums.setLayout(new BoxLayout(pnNums,BoxLayout.Y_AXIS));
        pnNums.add(L2);
        pnNums.add(one);
        pnNums.add(two);
        pnNums.add(three);
        pnNums.add(four);
        numOptions.add(one);
        numOptions.add(two);
        numOptions.add(three);
        numOptions.add(four);

        //sets up error message and spacers
        L3 = new JLabel("");
        L3.setForeground(Color.RED);
        JLabel spacer = new JLabel("\n");
        everything.add(spacer);
        JLabel spacer2 = new JLabel("\n");
        JLabel spacer3 = new JLabel("\n");

        //sets up next button
        arrow = new JButton("next");
        pnNums.add(spacer2);
        pnNums.add(arrow);
        everything.add(pnNums);
        everything.add(spacer3);
        everything.add(L3);
        arrow.addActionListener(this);
        add(everything);
    }

    public void question(String q, boolean isSoc, boolean agreeIsL){
        //setting up the answer selection
        L1.setText(index+2 + ") " + q);
        responses.clearSelection();
        numOptions.clearSelection();
        agreeIsLeft = agreeIsL;
        isSocial=isSoc;

        setSize(800,400);
        setVisible(true);

    }

// Just like with the real political compass test, positive values are libertarian and right-leaning

    public void actionPerformed(ActionEvent evt){
        L3.setText("");
        if(getSelectedResponse().equals("none") || getSelectedWeight()==0){
            L3.setText("Please select both a response and a weight before continuing.");
        }
        else if(getSelectedResponse().equals("Strongly agree")){
            if(isSocial && agreeIsLeft){
                currentSoc-=2*getSelectedWeight();
                socWeight+=getSelectedWeight();
                numSocQ++;
            }
            else if(isSocial){
                currentSoc+=2*getSelectedWeight();
                socWeight+=getSelectedWeight();
                numSocQ++;
            }
            else if(agreeIsLeft){
                currentEcon-=2*getSelectedWeight();
                econWeight+=getSelectedWeight();
                numEconQ++;
            }
            else{
                currentEcon+=2*getSelectedWeight();
                econWeight+=getSelectedWeight();
                numEconQ++;
            }
        }
        else if(getSelectedResponse().equals("Agree")){
            if(isSocial && agreeIsLeft){
                currentSoc-=getSelectedWeight();
                socWeight+=getSelectedWeight();
                numSocQ++;
            }
            else if(isSocial){
                currentSoc+=getSelectedWeight();
                socWeight+=getSelectedWeight();
                numSocQ++;
            }
            else if(agreeIsLeft){
                currentEcon-=getSelectedWeight();
                econWeight+=getSelectedWeight();
                numEconQ++;
            }
            else{
                currentEcon+=getSelectedWeight();
                econWeight+=getSelectedWeight();
                numEconQ++;
            }
        }
        else if(getSelectedResponse().equals("Disagree")){
            if(isSocial && agreeIsLeft){
                currentSoc+=getSelectedWeight();
                socWeight+=getSelectedWeight();
                numSocQ++;
            }
            else if(isSocial){
                currentSoc-=getSelectedWeight();
                socWeight+=getSelectedWeight();
                numSocQ++;
            }
            else if(agreeIsLeft){
                currentEcon+=getSelectedWeight();
                econWeight+=getSelectedWeight();
                numEconQ++;
            }
            else{
                currentEcon-=getSelectedWeight();
                econWeight+=getSelectedWeight();
                numEconQ++;
            }
        }
        else{
            if(isSocial && agreeIsLeft){
                currentSoc+=2*getSelectedWeight();
                socWeight+=getSelectedWeight();
                numSocQ++;
            }
            else if(isSocial){
                currentSoc-=2*getSelectedWeight();
                socWeight+=getSelectedWeight();
                numSocQ++;
            }
            else if(agreeIsLeft){
                currentEcon+=2*getSelectedWeight();
                econWeight+=getSelectedWeight();
                numEconQ++;
            }
            else{
                currentEcon-=2*getSelectedWeight();
                econWeight+=getSelectedWeight();
                numEconQ++;
            }
        }

        if(index<questions.length-1 && getSelectedWeight()!=0 && !getSelectedResponse().equals("none")){
            index++;
            if(index==questions.length-1){
                arrow.setText("submit");
            }
            question(questions[index],social[index],left[index]);
        }
        else if(getSelectedWeight()!=0 && !getSelectedResponse().equals("none")){
            double calcEcon = currentEcon/(econWeight/numEconQ)/numEconQ*5.0;
            double calcSoc = currentSoc/(socWeight/numSocQ)/numSocQ*5.0;
            DecimalFormat exact = new DecimalFormat("#.#");
            DecimalFormat notExact = new DecimalFormat("#.##");
            String formatEcon;
            String formatSoc;
            if(calcEcon%1==0){
                formatEcon=exact.format(calcEcon);
            }
            else{
                formatEcon=notExact.format(calcEcon);
            }
            if(calcSoc%1==0){
                formatSoc=exact.format(calcSoc);
            }
            else{
                formatSoc=notExact.format(calcSoc);
            }
            //got this off stack overflow
            try {
                Desktop.getDesktop().browse(new URL("http://www.politicalcompass.org/analysis2?ec=" + formatEcon +"&soc=" + formatSoc).toURI());
            } catch (Exception e) {}
            System.exit(0);

        }
    }

    private String getSelectedResponse(){
        if(strongAgree.isSelected()){
            return "Strongly agree";
        }
        if(agree.isSelected()){
            return "Agree";
        }
        if(disagree.isSelected()){
            return "Disagree";
        }
        if(strongDisagree.isSelected()){
            return "Strongly disagree";
        }
        return "none";
    }

    private double getSelectedWeight(){
        if(one.isSelected()){
            return 1;
        }
        if(two.isSelected()){
            return 1.333;
        }
        if(three.isSelected()){
            return 1.667;
        }
        if(four.isSelected()){
            return 2;
        }
        return 0;
    }


}
