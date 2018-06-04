
package com.example.danijela.sparkle.api;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.model.Comment;
import com.example.danijela.sparkle.model.Credentials;
import com.example.danijela.sparkle.model.DayItem;
import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.model.HabitStatistics;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.model.Message;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.model.Occurrence;
import com.example.danijela.sparkle.model.OccurrenceWeekly;
import com.example.danijela.sparkle.model.Result;
import com.example.danijela.sparkle.model.User;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// TODO: Replace all uses of this class before publishing your app.

public class MockRepository {

    public static final Map<Long, Habit> ITEM_MAP = new HashMap<Long, Habit>();

    public static final Map<Long, HabitSummary> LIST_ITEM_MAP = new HashMap<Long, HabitSummary>();

    public static final Map<Integer, User> USER_MAP = new HashMap<Integer, User>();

    static {

        createHabits();

        List<User> users = buildUsers();
        for (User user : users) {
            addUser(user);
        }
    }

    private static void createHabits() {


        LocalDate startDate = LocalDate.of(2016, 9, 20);
        HabitStatistics statistics = getHabitStatistics();
        DayItem[] dayItems = getHabitCalendarItems();

        User u1 = new User(1, "Anna", "Peterson", R.drawable.user_1);

        int[] weekItems = new int[]{1, 2, 3, 4};
        Occurrence occurrence = new OccurrenceWeekly(weekItems);

        Habit h1 = new Habit(1, "Take stairs", "Take stairs instead of elevator to increase overall fitness level", u1, startDate, occurrence, statistics, dayItems);
        h1.imageUrl = "http://img.freepik.com/free-photo/rear-view-of-young-woman-climbing-the-stairs_1139-145.jpg?size=338&ext=jpg";
        addItem(h1);
        addListItem(h1.getSummary());

        Habit h2 = new Habit(2, "Keep reading", "We read to know that we are not alone", u1, startDate, occurrence, statistics, dayItems);
        h2.imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlbnDl8IpR26E0LrL-VGg4AaBpoSHsHwVWukzwTBiOEYlV4Tx_WA";
        addItem(h2);
        addListItem(h2.getSummary());

        Habit h3 = new Habit(3, "Eat healthy", "To live long and prosper :)", u1, startDate, occurrence, statistics, dayItems);
        h3.imageUrl = "https://s-media-cache-ak0.pinimg.com/originals/5e/bb/43/5ebb4347b06771927da9c08d97e97606.jpg";
        h3.comments = buildComments(3);
        h3.notes = buildNotes(3);
        addItem(h3);
        addListItem(h3.getSummary());

        Habit h4 = new Habit(4, "Go to gym", "To build strong body", u1, startDate, occurrence, statistics, dayItems);
        h4.imageUrl = "http://nnimgt-a.akamaihd.net/transform/v1/crop/frm/storypad-JbL8dJ5dh2XzNFST9PPkaJ/c91afcaa-6a9f-4af2-96fc-e0cb3683499f.jpg/r0_0_3465_2310_w1200_h678_fmax.jpg";
        addItem(h4);
        addListItem(h4.getSummary());

        Habit h5 = new Habit(5, "Take a walk", "Nothing calms me down better then a walk", u1, startDate, occurrence, statistics, dayItems);
        h5.imageUrl = "https://s-media-cache-ak0.pinimg.com/originals/fe/a1/a7/fea1a7bd6154beea76aa53029f1a5667.jpg";
        addItem(h5);
        addListItem(h5.getSummary());

        Habit h6 = new Habit(6, "Drink smoothie a day", "To bust vitamin and minerals intake", u1, startDate, occurrence, statistics, dayItems);
        h6.imageUrl = "http://www.myorganicfoodclub.com/wp-content/uploads/smoothies-1.jpg";
        addItem(h6);
        addListItem(h6.getSummary());

        //User u1 = new User(1, "Anna", "Peterson", R.drawable.user_1);
        User u2 = new User(2, "Alejandro", "Parsons", R.drawable.user_2);
        u2.imageUrl = "https://s-media-cache-ak0.pinimg.com/236x/74/0d/3f/740d3f64768ebbc74370d2545d5fdff9.jpg";

        User u3 = new User(3, "Eleanor", "Brooke", R.drawable.user_3);
        u3.imageUrl = "https://media.licdn.com/mpr/mpr/shrinknp_200_200/p/1/005/0b0/377/39c9c78.jpg";

        User u4 = new User(4, "Maria", "Atwood", R.drawable.user_7);
        u4.imageUrl = "http://2.bp.blogspot.com/-dZKdgsUW2y0/Une2h3IIVMI/AAAAAAAAC1o/tqJJFHKzHfY/s1600/katrina-kaif-Complete-Profile.jpg";

        User u5 = new User(5, "Johnny", "Porter", R.drawable.user_5);
        u5.imageUrl = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQoDd36nVH087vJNq0uk6f1qgXl65yZGvGkCLdijvqbw1RpjVZn";

        User u6 = new User(6, "Johanna", "Guerra", R.drawable.user_6);
        u6.imageUrl = "https://s-media-cache-ak0.pinimg.com/originals/e3/02/17/e30217294be6d72767dfce484cfcb9a5.jpg";

        User u7 = new User(7, "Felipa", "Stallone", R.drawable.user_8);
        u7.imageUrl = "http://tumbledore.biz/wp-content/uploads/2016/05/celebrity-short-hairstyles-2015-the-best-short-hairstyles-for-women-recent-short-haircut-styles-2015.jpg";

        Habit oh1 = new Habit(8, "Push it up!", "Because I want to be strong", u1, startDate, occurrence, statistics, dayItems);
        oh1.imageUrl = "http://www.barbrothers.it/wp-content/uploads/2016/08/push-ups.jpg";
        oh1.comments = buildComments(oh1.id);
        oh1.notes = buildNotes(oh1.id);

        Habit oh2 = new Habit(9, "Tidy up", "Clear space, clear mind", u1, startDate, occurrence, statistics, dayItems);
        oh2.imageUrl = "https://s-media-cache-ak0.pinimg.com/originals/d2/b9/b2/d2b9b232ac2ab293d5d15769c62fa5b4.jpg";

        Habit oh3 = new Habit(10, "Get enough sleep", "Well rested mind works best", u2, startDate, occurrence, statistics, dayItems);
        oh3.imageUrl = "https://s-media-cache-ak0.pinimg.com/474x/91/a7/14/91a714ebcfcda8e25ec308f8131eb7d4.jpg";

        Habit oh4 = new Habit(11, "Write", "To increase writing skills", u2, startDate, occurrence, statistics, dayItems);
        oh4.imageUrl = "https://s-media-cache-ak0.pinimg.com/474x/03/8f/ae/038fae99aacd320595b94728a086727b.jpg";

        Habit oh5 = new Habit(12, "Stretch", "Healthy body, healthy spirit", u3, startDate, occurrence, statistics, dayItems);
        oh5.imageUrl = "https://s-media-cache-ak0.pinimg.com/564x/a6/e4/1d/a6e41db2d4de8d7ad9edec9d7c0eaa8d.jpg";

        Habit oh6 = new Habit(13, "Squat", "To increase strength and look fabulous", u4, startDate, occurrence, statistics, dayItems);
        oh6.imageUrl = "https://s-media-cache-ak0.pinimg.com/474x/61/a0/94/61a094b629ee282a78159185a59ca5f3.jpg";

        Habit oh7 = new Habit(14, "Take stairs", "To get extra steps in day", u5, startDate, occurrence, statistics, dayItems);
        oh7.imageUrl = "http://img.freepik.com/free-photo/rear-view-of-young-woman-climbing-the-stairs_1139-145.jpg?size=338&ext=jpg";

        Habit oh8 = new Habit(15, "Eat vegetable rich dinner", "Because beauty comes from the inside", u6, startDate, occurrence, statistics, dayItems);
        oh8.imageUrl = "https://s-media-cache-ak0.pinimg.com/474x/dc/21/33/dc2133f197be7fba580191b74a5ab71c.jpg";

        Habit oh9 = new Habit(16, "Early rising", "Makes a man healthy, wealthy and wise :)", u6, startDate, occurrence, statistics, dayItems);
        oh9.imageUrl = "http://image.slidesharecdn.com/wakeupearly-151231185650/95/waking-up-early-workshop-slides-2-638.jpg?cb=1451588318";

        Habit oh10 = new Habit(17, "Dress up", "To feel fabulous", u7, startDate, occurrence, statistics, dayItems);
        oh10.imageUrl = "https://s-media-cache-ak0.pinimg.com/474x/e2/33/67/e2336795db027155cf8a2cc13fce46ed.jpg";

        Habit oh11 = new Habit(18, "Take a mind puzzle", "To keep that mind sharp", u7, startDate, occurrence, statistics, dayItems);
        oh11.imageUrl = "http://previews.123rf.com/images/get4net/get4net1011/get4net101100508/8246895-illustration-of-man-s-mind-with-jigsaw-puzzle-on-white-background-Stock-Vector.jpg";

        Habit oh12 = new Habit(19, "Sunbath", "To get enough of vitamin D", u7, startDate, occurrence, statistics, dayItems);
        oh12.imageUrl = "http://hdwallpapergirls.com/assets/large/girl-enjoy-summer-sun-wallpaper.jpg";

        Habit oh13 = new Habit(20, "Drink green tea", "Instead of coffee", u7, startDate, occurrence, statistics, dayItems);
        oh13.imageUrl = "http://fitnessandhealthadvisor.com/wp-content/uploads/2013/05/greentea3.jpg";

        Habit oh14 = new Habit(21, "Dance", "It is best psychotherapy and exercise", u7, startDate, occurrence, statistics, dayItems);
        oh14.imageUrl = "https://s-media-cache-ak0.pinimg.com/564x/96/95/e6/9695e6ad871a67baff8b69473882c983.jpg";

        Habit oh15 = new Habit(22, "Run", "Just do it", u2, startDate, occurrence, statistics, dayItems);
        oh15.imageUrl = "https://s-media-cache-ak0.pinimg.com/474x/80/7e/7f/807e7f057698f17d226b74ac15a092dc.jpg";

        Habit oh16 = new Habit(23, "Learn something relevant", "To advance in skills", u5, startDate, occurrence, statistics, dayItems);
        oh16.imageUrl = "http://www.cambridgeenglish.org/static-assets/Images/feature-landing_tcm32-288767.jpg";
        //http://blogs.articulate.com/rapid-elearning/wp-content/uploads/sites/7/2016/07/350-learning-interactions.png


        Habit oh17 = new Habit(24, "Take a cold shower", "To exercise willpower and self discipline", u3, startDate, occurrence, statistics, dayItems);
        oh17.imageUrl = "http://www.votemeyo.com/Images/Content/23_10_2014_1_13_39_785.jpg";

        Habit oh18 = new Habit(25, "Workout", "To feel and look great", u4, startDate, occurrence, statistics, dayItems);
        oh18.imageUrl = "http://www.superskinnyme.com/blog/wp-content/uploads/2015/01/full-body-workout-plan-weight-loss.jpg";

        addItem(oh1);
        addListItem(oh1.getSummary());
        addItem(oh2);
        addListItem(oh2.getSummary());
        addItem(oh3);
        addListItem(oh3.getSummary());
        addItem(oh4);
        addListItem(oh4.getSummary());
        addItem(oh5);
        addListItem(oh5.getSummary());
        addItem(oh6);
        addListItem(oh6.getSummary());
        addItem(oh7);
        addListItem(oh7.getSummary());
        addItem(oh8);
        addListItem(oh8.getSummary());
        addItem(oh9);
        addListItem(oh9.getSummary());
        addItem(oh10);
        addListItem(oh10.getSummary());
        addItem(oh11);
        addListItem(oh11.getSummary());
        addItem(oh12);
        addListItem(oh12.getSummary());
        addItem(oh13);
        addListItem(oh13.getSummary());
        addItem(oh14);
        addListItem(oh14.getSummary());
        addItem(oh15);
        addListItem(oh15.getSummary());
        addItem(oh16);
        addListItem(oh16.getSummary());
        addItem(oh17);
        addListItem(oh17.getSummary());
        addItem(oh18);
        addListItem(oh18.getSummary());
    }

    private static ArrayList<Comment> buildComments(long habitId) {
        ArrayList<Comment> comments = new ArrayList<>();

        User u1 = new User(1, "Anna", "Peterson", R.drawable.user_1);
        User u2 = new User(2, "Alejandro", "Parsons", R.drawable.user_2);
        User u3 = new User(3, "Eleanor", "Seabrooke", R.drawable.user_3);

        Comment c1 = new Comment(1, habitId, "wooow, awesome work!", LocalDate.of(2016, 1, 13), u1);
        Comment c2 = new Comment(2, habitId, "nice work!", LocalDate.of(2016, 1, 13), u2);
        Comment c3 = new Comment(3, habitId, "keep the good pace", LocalDate.of(2016, 1, 13), u3);
        Comment c4 = new Comment(4, habitId, "wooow!", LocalDate.of(2016, 1, 13), u2);
        Comment c5 = new Comment(5, habitId, "Where have you bought those runnning shoes?", LocalDate.of(2016, 1, 13), u3);
        Comment c6 = new Comment(6, habitId, "Those are order from e-bay", LocalDate.of(2016, 1, 13), u1);
        Comment c7 = new Comment(7, habitId, "I like them", LocalDate.of(2016, 1, 13), u3);

        comments.add(c1);
        comments.add(c2);
        comments.add(c3);
        comments.add(c4);
        comments.add(c5);
        comments.add(c6);
        comments.add(c7);

        return comments;
    }

    private static ArrayList<Note> buildNotes(long habitId) {
        ArrayList<Note> notes = new ArrayList<>();
        Note n1 = new Note(1, habitId, "Had some great lunch! Making progress every day!", LocalDate.of(2016, 1, 11), LocalDate.of(2016, 1, 11), "http://www.kissmybroccoliblog.com/wp-content/uploads/2013/04/Veggies-Dip.jpg");
        Note n2 = new Note(2, habitId, "Just prepared tasty vegetable wrap!", LocalDate.of(2016, 1, 12), LocalDate.of(2016, 1, 11), "http://www.thefoodiemethod.com/storage/post-images/027_roastedveggies1.jpg?__SQUARESPACE_CACHEVERSION=1319289853666");
        Note n3 = new Note(3, habitId, "Still on track, feeling great!", LocalDate.of(2016, 1, 13), LocalDate.of(2016, 1, 11), "http://paleogrubs.com/wp-content/uploads/2014/07/veggieplatter.jpg");
        Note n4 = new Note(4, habitId, "One more day..I don't miss junk food anymore", LocalDate.of(2016, 1, 15), LocalDate.of(2016, 1, 11), "https://s-media-cache-ak0.pinimg.com/originals/64/a8/ee/64a8ee0593b2caf2caf76bff64163613.jpg");

        notes.add(n1);
        notes.add(n2);
        notes.add(n3);
        notes.add(n4);
        return notes;
    }

    private static ArrayList<Message> buildMessages() {
        ArrayList<Message> items = new ArrayList<>();

        User u1 = new User(1, "Anna", "Peterson", R.drawable.user_1);
        User u2 = new User(2, "Alejandro", "Parsons", R.drawable.user_2);
        User u3 = new User(3, "Eleanor", "Seabrooke", R.drawable.user_3);
        User u4 = new User(4, "Maria", "Attwood", R.drawable.user_7);
        User u5 = new User(5, "Johnny", "Porter", R.drawable.user_5);
        User u6 = new User(6, "Johanna", "Guerra", R.drawable.user_6);
        User u7 = new User(7, "Felipa", "Stallone", R.drawable.user_8);

        Message m1 = new Message(1, "Hello, you are sdoing such a nice work with your new habits.", new Date(), u1, u7);
        Message m2 = new Message(2, "Hi, why have you quit working out? You had such an amazing streak! Don't quit :) ", new Date(), u3, u7);
        Message m3 = new Message(3, "I see that you liked my log entry today :)", new Date(), u4, u7);
        Message m4 = new Message(4, "Hi, I see you are doing a lot of running. Do you have any tip how to prevent blisters?", new Date(), u5, u7);
        Message m5 = new Message(5, "", new Date(), u6, u7);
        Message m6 = new Message(6, "Hi, thank you for your support! It feels so much easier when you feel supported!", new Date(), u3, u7);
        Message m7 = new Message(7, "It has been a while since we haven't heard form each other. How are you doing? Still on track?", new Date(), u2, u7);

        items.add(m1);
        items.add(m2);
        items.add(m3);
        items.add(m4);
        items.add(m5);
        items.add(m6);
        items.add(m7);

        return items;
    }

    private static ArrayList<Message> buildMessagesForUser(User user) {

        ArrayList<Message> items = new ArrayList<>();

        User u7 = new User(7, "Felipa", "Stallone", R.drawable.user_8);


        Message m2 = new Message(2, "Hi, why have you quit working out? You had such an amazing streak! Don't quit :) ", new Date(), u7, user);
        Message m3 = new Message(3, "Oh, thank your for noticing! I see you are doing great your self! ", new Date(), user, u7);
        Message m4 = new Message(4, "I am tryint to get out of the coutch, but it's soo cold outside :) ", new Date(), u7, user);
        Message m5 = new Message(5, "You know that Nike comertial - Just do it :) ", new Date(), user, u7);
        Message m6 = new Message(6, "Hehe, I will! But my brain just came with some awesome excuses - it would be shame to through them away! :D ", new Date(), u7, user);
        Message m7 = new Message(7, "Haha, you can save them for tomorrow - no excuses today :)", new Date(), user, u7);

        items.add(m2);
        items.add(m3);
        items.add(m4);
        items.add(m5);
        items.add(m6);
        items.add(m7);

        return items;

    }

    private static ArrayList<User> buildUsers() {
        ArrayList<User> users = new ArrayList<>();

        User u1 = new User(1, "Anna", "Peterson", R.drawable.user_1);
        User u2 = new User(2, "Alejandro", "Parsons", R.drawable.user_2);
        User u3 = new User(3, "Eleanor", "Seabrooke", R.drawable.user_3);
        User u4 = new User(4, "Maria", "Attwood", R.drawable.user_7);
        User u5 = new User(5, "Johnny", "Porter", R.drawable.user_5);
        User u6 = new User(6, "Johanna", "Guerra", R.drawable.user_6);
        User u7 = new User(7, "Felipa", "Stalone", R.drawable.user_8);

        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
        users.add(u6);
        users.add(u7);

        return users;
    }

    private static DayItem[] buildCalendarItems() {

        LocalDate date = LocalDate.of(2016, 10, 1);

        DayItem i1 = new DayItem(date, Result.Fail);
        DayItem i2 = new DayItem(date.plusDays(1), Result.Fail);
        DayItem i3 = new DayItem(date.plusDays(2), Result.Success);
        DayItem i4 = new DayItem(date.plusDays(3), Result.Success);
        DayItem i5 = new DayItem(date.plusDays(4), Result.Fail);
        DayItem i6 = new DayItem(date.plusDays(5), Result.Success);
        DayItem i7 = new DayItem(date.plusDays(6), Result.Success);
        DayItem i8 = new DayItem(date.plusDays(7), Result.Success);
        DayItem i9 = new DayItem(date.plusDays(8), Result.Success);
        DayItem i10 = new DayItem(date.plusDays(9), Result.Success);
        DayItem i11 = new DayItem(date.plusDays(10), Result.Success);
        DayItem i12 = new DayItem(date.plusDays(15), Result.Success);
        DayItem i13 = new DayItem(date.plusDays(16), Result.Fail);
        DayItem i14 = new DayItem(date.plusDays(17), Result.Success);
        DayItem i15 = new DayItem(date.plusDays(18), Result.Fail);
        DayItem i16 = new DayItem(date.plusDays(19), Result.Success);

        return new DayItem[]{
                i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16};

    }

    private static void addListItem(HabitSummary item) {
        LIST_ITEM_MAP.put(item.id, item);
    }

    private static void addItem(Habit item) {
        ITEM_MAP.put(item.id, item);
    }

    public static ArrayList<HabitSummary> getUserHabits(int userId) {
        ArrayList<HabitSummary> filtered = new ArrayList<>();
        for (HabitSummary habit : LIST_ITEM_MAP.values()) {
            if (habit.user != null && habit.user.id == userId) {
                filtered.add(habit);
            }
        }

        return filtered;
    }

    public static List<HabitSummary> getHabits() {
        List<HabitSummary> items = new ArrayList<>();

        for (HabitSummary item : LIST_ITEM_MAP.values()) {
            if (item.userId != Credentials.getLoggedUser().id) {
                items.add(item);
            }
        }
        return items;
    }

    public static Habit getHabit(int userId, long id) {
        return ITEM_MAP.get(id);
    }

    public static Habit createHabit(Habit item) {
        item.id = LIST_ITEM_MAP.get(LIST_ITEM_MAP.size() - 1).id + 1;

        addItem(item);
        addListItem(item.getSummary());
        return item;
    }

    public static ArrayList<Habit> filter(String pattern) {
        ArrayList<Habit> filtered = new ArrayList<>();
        for (Habit habit : ITEM_MAP.values()) {
            if (habit.user != null && habit.title.toLowerCase().contains(pattern)) {
                filtered.add(habit);
            }
        }

        return filtered;
    }

    public static ArrayList<Habit> filterMyHabits(String pattern) {
        ArrayList<Habit> filtered = new ArrayList<>();
        for (Habit habit : ITEM_MAP.values()) {
            if (habit.user != null && habit.title.toLowerCase().contains(pattern)) {
                filtered.add(habit);
            }
        }
        return filtered;
    }

    public static Note createNote(int userId, long habitId, Note item) {
        Habit habit = getHabit(userId, habitId);
        habit.notes.add(item);

        item.habitId = habitId;
        return item;
    }

    public static Note editNote(int userId, long habitId, Note item) {
        Habit habit = getHabit(userId, habitId);
        habit.notes.add(item);

        item.habitId = habitId;
        return item;
    }

    public static Void deleteNote(int userId, long habitId, long id) {
        Habit habit = getHabit(userId, habitId);

        List<Note> notes = habit.notes;
        for (Note note : notes) {
            if (note.id == id) {
                //TODO: remove
            }
        }

        return null;
    }

    public static Comment createComment(int userId, long habitId, Comment item) {
        Habit habit = getHabit(userId, habitId);
        habit.comments.add(item);
        item.habitId = habitId;
        return item;
    }

    public static Comment editComment(int userId, long habitId, Comment item) {
        //Habit habit = getHabit(userId, habitId);
        return item;
    }

    public static Void deleteComment(int userId, long habitId, long id) {
        Habit habit = getHabit(userId, habitId);
        //todo: remove
        //habit.getComments().remove(item);
        return null;
    }

    public static DayItem createDayResult(int userId, long habitId, DayItem item) {
        Habit habit = getHabit(userId, habitId);
        habit.dayItems.add(item);
        return item;
    }

    public static DayItem editDayResult(int userId, long habitId, DayItem item) {
        Habit habit = getHabit(userId, habitId);
        //TODO: edit
        return item;
    }

    public static ArrayList<User> getFollowing(User user) {
        return buildUsers();
    }

    public static ArrayList<User> getFollowers(User user) {
        return buildUsers();
    }

    public static ArrayList<Message> getMessages() {
        return buildMessages();
    }

    public static ArrayList<Message> getMessagesForUser(User user) {
        return buildMessagesForUser(user);
    }

    public static ArrayList<Comment> getCommentsForHabit(long habitId) {
        return buildComments(habitId);
    }

    public static HabitStatistics getHabitStatistics() {
        return new HabitStatistics(LocalDate.of(2016, 9, 16), 6, 18, 60);
    }

    public static DayItem[] getHabitCalendarItems() {
        return buildCalendarItems();
    }


    private static User addUser(User user) {
        if (user.id > 0) {
            if (!USER_MAP.containsKey(user.id)) {
                USER_MAP.put(user.id, user);
            }
        } else {
            user.id = USER_MAP.size() + 1;
        }

        return user;
    }

    public static User getUser(int id) {
        User user = USER_MAP.get(id);

        return user;
    }
}


