package utility;

public class Associations {
    public static class Pair<T> {
        private T s1;
        private T s2;

        public Pair(T s1, T s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        public Pair(T[] segs) {
            if (segs.length != 2) {
                throw new RuntimeException("Array must be of length 2");
            }
            s1 = segs[0];
            s2 = segs[1];
        }

        public T first() {
            return s1;
        }

        public T second() {
            return s2;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            } else if (other.getClass() != this.getClass()) {
                return false;
            }
            var otherAsThis = (Pair<T>) other;
            return s1.equals(otherAsThis.s1) && s2.equals(otherAsThis.s2);
        }
    }

    public static class Quad<T> {
        private T s1;
        private T s2;
        private T s3;
        private T s4;

        public Quad(T s1, T s2, T s3, T s4) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
        }

        public Quad(T[] strs) {
            if (strs.length != 4) {
                throw new RuntimeException("Array must be of length 4");
            }
            s1 = strs[0];
            s2 = strs[1];
            s3 = strs[2];
            s4 = strs[3];
        }

        public T first() {
            return s1;
        }

        public T second() {
            return s2;
        }

        public T third() {
            return s3;
        }

        public T fourth() {
            return s4;
        }
    }
}
