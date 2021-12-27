package Day24.ProcessorRepairs.ALU;

import Day24.ProcessorRepairs.ALU.Exeptions.InvalidMemmoryAddressExeption;
import Day24.ProcessorRepairs.ALU.Exeptions.UnsupportedInstructionExeption;

import java.util.List;

public class ALU {
    int nextInput;
    String input;
    private Int x;
    private Int y;
    private Int z;
    private Int w;

    public void run(List<String> program, String input) {
        run(program, input, 0, 0, 0, 0);
    }

    public void run(List<String> program, String input, int w, int x, int y, int z) {
        nextInput = 0;
        this.w = new Int(w);
        this.x = new Int(x);
        this.y = new Int(z);
        this.z = new Int(z);
        this.input = input;

        for (var command : program) {
            interprete(command);
        }
        System.out.println("fin\nw: " + this.w.value + "\nx: " + this.x.value + "\ny: " + this.y.value + "\nz: " + this.z.value);
    }

    private void interprete(String instruction) {
        String[] argmuents = instruction.split(" ");
        switch (argmuents[0]) {
            case "inp":
                invoke_inp(argmuents[1].charAt(0));
                break;
            case "add":
                invoke_add(argmuents[1].charAt(0), argmuents[2]);
                break;
            case "mul":
                invoke_mul(argmuents[1].charAt(0), argmuents[2]);
                break;
            case "div":
                invoke_div(argmuents[1].charAt(0), argmuents[2]);
                break;
            case "mod":
                invoke_mod(argmuents[1].charAt(0), argmuents[2]);
                break;
            case "eql":
                invoke_eql(argmuents[1].charAt(0), argmuents[2]);
                break;
            case "zro":
                invoke_zro(argmuents[1].charAt(0));
                break;
            default:
                throw new UnsupportedInstructionExeption(instruction);
        }
    }

    private void invoke_inp(char a) {
        Int save = getFromChar_Ref(a);
        save.value = input.charAt(nextInput++) - 48;
    }

    private void invoke_add(char a, String b) {
        Int save = getFromChar_Ref(a);
        int add = getFromChar_Value(b);
        save.value = save.value + add;
    }

    private void invoke_mul(char a, String b) {
        Int save = getFromChar_Ref(a);
        int mul = getFromChar_Value(b);
        save.value = save.value * mul;
    }

    private void invoke_div(char a, String b) {
        Int save = getFromChar_Ref(a);
        int div = getFromChar_Value(b);
        save.value = save.value / div;
    }

    private void invoke_mod(char a, String b) {
        Int save = getFromChar_Ref(a);
        int mod = getFromChar_Value(b);
        save.value = save.value % mod;
    }

    private void invoke_eql(char a, String b) {
        Int save = getFromChar_Ref(a);
        int eql = getFromChar_Value(b);
        save.value = save.value == eql ? 1 : 0;
    }

    private void invoke_zro(char a) {
        Int save = getFromChar_Ref(a);
        save.value = 0;
    }

    private Int getFromChar_Ref(char c) {
        switch (c) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
            case 'w':
                return w;
            default:
                throw new InvalidMemmoryAddressExeption(c);
        }
    }

    private int getFromChar_Value(String c) {
        switch (c) {
            case "x":
                return x.value;
            case "y":
                return y.value;
            case "z":
                return z.value;
            case "w":
                return w.value;
            default:
                return Integer.parseInt(c);
        }
    }

    public int getW() {
        return w.value;
    }

    public int getX() {
        return x.value;
    }

    public int getY() {
        return y.value;
    }

    public int getZ() {
        return z.value;
    }
}
