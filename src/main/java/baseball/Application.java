package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.*;
public class Application {
    public static void main(String[] args) {
        boolean continueGame = true;
        System.out.println("숫자 야구 게임을 시작합니다.");

        while(continueGame){
            numBaseballGame();
            continueGame = approveOfContinueGame();
        }
    }

    public static void numBaseballGame(){
        List<Integer> computerNum = new ArrayList<>();

        while (true){
            if(!createComputerNum(computerNum))
                throw new IllegalArgumentException("컴퓨터가 랜덤수를 생성하지 못하였습니다.");

            List<Integer> playerNum = new ArrayList<>(inputPlayerNum());

            checkValidPlayerNum(playerNum);

            StrikeBallResult strikeBallResult = comparePlayerAndComputer(playerNum, computerNum);
            notifyGameResult(strikeBallResult);

            boolean answer = isAnswer(strikeBallResult.strikeCnt);

            if(answer)
                return;
        }
    }

    public static boolean createComputerNum(List<Integer> computer){
        while(computer.size() < 3){
            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(randomNumber)) {
                computer.add(randomNumber);
            }
        }
        if(computer.size() == 3)
            return true;
        else
            return false;
    }

    public static List<Integer> inputPlayerNum(){
        List<Integer> player = new ArrayList<>();
        System.out.print("숫자를 입력해주세요 : ");
        String playerNum = Console.readLine();

        for (int i = 0; i < playerNum.length(); i++) {
            player.add(playerNum.charAt(i)-'0');
        }
        return player;
    }

    public static boolean isValidLength(int playerNumLength){
        if(playerNumLength != 3)
            return false;
        else
            return true;
    }

    public static boolean isExistDuplicate(List<Integer> player){
        int hundreds = player.get(0);
        int tens = player.get(1);
        int units = player.get(2);

        if(hundreds == tens || hundreds == units || tens == units)
            return true;
        else
            return false;
    }

    public static boolean checkValidPlayerNum(List<Integer> player){
        if(!isValidLength(player.size()))
            throw new IllegalArgumentException("사용자가 잘못된 값을 입력하였습니다.");

        if(isExistDuplicate(player))
            throw new IllegalArgumentException("사용자가 중복된 숫자를 입력하였습니다.");

        return true;
    }

    public static StrikeBallResult comparePlayerAndComputer(List<Integer> player , List<Integer> computer){
        StrikeBallResult strikeBallResult = new StrikeBallResult();

        int playerUnits = player.get(0);
        int computerUnits = computer.get(0);
        int playerTens = player.get(1);
        int computerTens = computer.get(1);
        int playerHundreds = player.get(2);
        int computerHundreds = computer.get(2);

        if(playerUnits == computerUnits)
            strikeBallResult.strike();
        else if(playerUnits == computerTens || playerUnits == computerHundreds)
            strikeBallResult.ball();

        if(playerTens == computerTens)
            strikeBallResult.strike();
        else if(playerTens == computerUnits || playerTens == computerHundreds)
            strikeBallResult.ball();

        if(playerHundreds == computerHundreds)
            strikeBallResult.strike();
        else if(playerHundreds == computerTens || playerHundreds == computerUnits)
            strikeBallResult.ball();

        return strikeBallResult;
    }

    public static void notifyGameResult(StrikeBallResult strikeBallResult){
        if(strikeBallResult.getStrikeCnt() == 3){
            System.out.println("3스트라이크");
            System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        }else if(strikeBallResult.getStrikeCnt() + strikeBallResult.getBallCnt() == 0){
            System.out.println("낫싱");
        }else if(strikeBallResult.getBallCnt() == 0){
            System.out.println(strikeBallResult.getStrikeCnt() + "스트라이크" );
        }else{
            System.out.println(strikeBallResult.getBallCnt() + "볼 " + strikeBallResult.getStrikeCnt() + "스트라이크");
        }
    }

    public static boolean isAnswer(int strikeCnt){
        if(strikeCnt == 3)
            return true;
        else
            return false;
    }

    public static void checkValidPlayerReply(String playerReply){
        if(playerReply.length() != 1)
            throw new IllegalArgumentException("사용자가 잘못된 값을 입력하였습니다.");

        if(playerReply.charAt(0) != '1' && playerReply.charAt(0) != '2')
            throw new IllegalArgumentException("사용자가 잘못된 값을 입력하였습니다.");
    }

    public static boolean approveOfContinueGame(){
        String playerReply = Console.readLine();
        checkValidPlayerReply(playerReply);

        if("1".equals(playerReply.charAt(0)))
            return true;
        else
            return false;
    }

    public static class StrikeBallResult{
        int strikeCnt;
        int ballCnt;

        public void strike(){
            this.strikeCnt++;
        }

        public void ball(){
            this.ballCnt++;
        }

        public StrikeBallResult() {
            this.strikeCnt = 0;
            this.ballCnt = 0;
        }

        public int getStrikeCnt() {
            return strikeCnt;
        }

        public int getBallCnt() {
            return ballCnt;
        }
    }
}
