package Janelas;

public class TestRun {
    public static void main(String[] args) {
        try {
            System.out.println("Starting...");
            CadastroMedicamento cm = new CadastroMedicamento();
            System.out.println("Constructor passed!");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Error e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
