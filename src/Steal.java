import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.SceneObject;

public class Steal implements Strategy
{
    private Stall foodStall = new Stall("Food stall", 4875, 1);
    private Stall generalStall = new Stall("General stall", 4876, 60);
    private Stall magicStall = new Stall("Magic stall", 4877, 65);
    private Stall scimitarStall = new Stall("Scimitar stall", 4878, 80);

    private SceneObject stall;

    @Override
    public boolean activate()
    {
        for (SceneObject so : SceneObjects.getNearest(getStall().getObjectId()))
        {
            stall = so;

            return true;
        }

        return false;
    }

    @Override
    public void execute()
    {
        if (stall != null)
        {
            MinimalThieving.status = "Thieving " + getStall().getName();

            stall.interact(0);

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return Players.getMyPlayer().getAnimation() != -1;
                }
            }, 1000);
        }
    }

    public Stall getStall()
    {
        int level = Skill.THIEVING.getLevel();

        if (level < generalStall.getRequiredLevel())
        {
            return foodStall;
        }
        else if (level >= generalStall.getRequiredLevel()
                && level < magicStall.getRequiredLevel())
        {
            return generalStall;
        }
        else if (level >= magicStall.getRequiredLevel()
                && level < scimitarStall.getRequiredLevel())
        {
            return magicStall;
        }
        else
        {
            return scimitarStall;
        }
    }
}