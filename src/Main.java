public class Main {

    public static void main(String[] args) {
        while(true) {
            try {
                Thread t = new Thread(new TCPServer());
                t.start();
                t.join();
            }
            catch (Exception e){
            }
        }
    }
}
