package war.kata;

public class Roman {
    public enum Numerals {
        I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

        private int value;

        private Numerals(int value) {
            this.value = value;
        }
    }

    public String GetNumerals(int i) {
        for (Numerals n: Numerals.values())
            System.out.println(n + " " + n.value);
        return "";
    }
}
