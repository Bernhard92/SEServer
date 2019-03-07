import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ServerThread implements Runnable {

    private Socket clientSocket;
    private int matrNumber = 0;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public void run() {
//        System.out.println("Connection received from " + clientSocket.getInetAddress().getHostName());

        try {
            BufferedReader inFromClient = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(
                    clientSocket.getOutputStream());

            String clientSentence = inFromClient.readLine();
            if (clientSentence != null) {
                String answerMessage = checkMatrNumber(clientSentence) + '\n';
                outToClient.writeBytes(answerMessage);
            }

            inFromClient.close();
            outToClient.close();
            clientSocket.close();

            Logger.getLogger().storeAccess(clientSocket.getInetAddress().getHostName(), clientSentence);
        } catch (Exception e) {
            Logger.getLogger().log(e.getMessage());
        }
    }

    private String checkMatrNumber(String clientSentence) {
        String failMessage = "Dies ist keine gueltige Matrikelnummer";
        String answer = "";

        if (clientSentence == null)
            return failMessage;

        if (clientSentence.length() != 8 ||
                !(clientSentence.charAt(0) >= '0' && clientSentence.charAt(0) <= '4'))
            return failMessage;

        int yearNumber;
        try {
            yearNumber = Integer.parseInt(clientSentence.substring(1, 3));
            matrNumber = Integer.parseInt(clientSentence);
        } catch (NumberFormatException e) {
            return failMessage;
        }

        if (yearNumber < 25) {
            yearNumber += 2000;
        } else {
            yearNumber += 1900;
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");

        try {
            Date date1 = simpleDateFormat.parse("01/10/" + String.valueOf(yearNumber) + " 00:00:00");
            Date date2 = simpleDateFormat.parse(simpleDateFormat.format((new Date())));

            answer = calcDifference(date1, date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return failMessage;
        }

        return answer;
    }

    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    private String calcDifference(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        return "Sie haben vor " + elapsedDays + " Tagen, " + elapsedHours + " Stunden, "
                + elapsedMinutes + " Minuten und " + elapsedSeconds + " Sekunden, begonnen zu studieren";

    }

}
