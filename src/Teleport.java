import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.Loader;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;

public class Teleport implements Strategy
{
    @Override
    public boolean activate()
    {
        return SceneObjects.getNearest(Stall.getBestStall().getId()).length == 0
                && Relog.isLoggedIn();
    }

    @Override
    public void execute()
    {
        MinimalThieving.status = "Teleporting back to stalls";

        System.out.println("Message: " + Loader.getClient().getInterfaceCache()[372].getMessage());

        if (Game.getOpenBackDialogId() != 2459)
        {
            Menu.clickButton(1195);

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return Game.getOpenBackDialogId() == 2459;
                }
            }, 2500);
        }

        if (Game.getOpenBackDialogId() == 2459)
        {
            Menu.sendAction(315, -1, -1, 2461);

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return Players.getMyPlayer().getAnimation() == -1
                            && SceneObjects.getNearest(Stall.getBestStall().getId()).length > 0;
                }
            }, 5000);
        }
    }
}