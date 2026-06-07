package hashmap;

import java.util.Objects;

public class TestKey {

    private final String value;

    public TestKey(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestKey testKey = (TestKey) o;
        return Objects.equals(value, testKey.value);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return value;
    }
}
