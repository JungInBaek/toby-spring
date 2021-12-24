package springbook.user.dao;

import springbook.user.domain.User;

import java.util.List;

public interface UserDao {

    public void add(User user);

    public User get(String id);

    public List<User> getAll();

    public void deleteAll();

    public int getCount();

    public void update(User user);
}
