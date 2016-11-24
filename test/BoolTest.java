import org.junit.Test;

public class BoolTest {
    @Test
    public void should() {
        System.out.println(greater(2, 3).ifTrue(() -> 5));
    }

    public Bool greater(int a, int b) {
        return a > b ? Bool.True : Bool.False;
    }

    enum Bool {
        True {
            @Override
            <T> T ifTrue(Statement<T> statement) {
                return statement.action();
            }
        }, False {
            @Override
            <T> T ifFalse(Statement<T> statement) {
                return statement.action();
            }
        };

        <T> T ifTrue(Statement<T> statement) {
            return null;
        }

        <T> T ifFalse(Statement<T> statement) {
            return null;
        }
    }

    interface Statement<T> {
        T action();
    }
}
