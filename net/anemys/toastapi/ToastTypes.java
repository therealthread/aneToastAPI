package net.anemys.toastapi;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ToastTypes
{

    public static final ToastLogic GOAL = Goal.build();
    public static final ToastLogic TASK = Task.build();
    public static final ToastLogic CHALLENGE = Challenge.build();

    public sealed interface ToastLogic
            permits Custom, Goal, Task, Challenge
    {

        String value();

        /*
        * More can be added in the future, and this guarantees
        * usability even without them.
        * */
        @Contract("_, -> new")
        static ToastLogic of(String value)
        {
            return new Custom(value);
        }
    }

    private record Custom(String value)
            implements ToastLogic
    {
    }

    private record Goal(String value)
            implements ToastLogic
    {
        @Contract(" -> new")
        static Goal build()
        {
            return new Goal("goal");
        }
    }

    private record Task(String value)
            implements ToastLogic
    {
        @Contract(" -> new")
        static Task build()
        {
            return new Task("task");
        }
    }

    private record Challenge(String value)
            implements ToastLogic
    {
        @Contract(" -> new")
        static Challenge build()
        {
            return new Challenge("challenge");
        }
    }

}
