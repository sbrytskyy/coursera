
public class User {

    private int id;
    
    public User(int id) {
        this.id = id;
    }

    public boolean equals(User y) { // does this board equal y?
        if (this == y)
            return true;
        if (!(y instanceof User))
            
            return false;
        User b2 = (User) y;

        if (id != b2.id)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + "]";
    }

    public int manhattan() {
        return id;
    }
}
