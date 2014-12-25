import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.SceneObject;

public class Steal implements Strategy
{
    private Stall foodStall = new Stall(4875, 1);
    private Stall craftingStall = new Stall(4874, 30);
    private Stall generalStall = new Stall(4867, 60);
    private Stall magicStall = new Stall(4877, 65);
    private Stall scimitarStalll = new Stall(4878, 80);

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
            MinimalThieving.status = "Thieving";

            stall.interact(0);

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return Players.getMyPlayer().getAnimation() != -1;
                }
            }, 2000);
        }
    }

    public Stall getStall()
    {
        int level = Skill.THIEVING.getLevel();

        if (level < craftingStall.getRequiredLevel())
        {
            return foodStall;
        }
        else if (level >= craftingStall.getRequiredLevel()
                && level < generalStall.getRequiredLevel())
        {
            return craftingStall;
        }
        else if (level >= generalStall.getRequiredLevel()
                && level < magicStall.getRequiredLevel())
        {
            return generalStall;
        }
        else if (level >= magicStall.getRequiredLevel()
                && level < scimitarStalll.getRequiredLevel())
        {
            return magicStall;
        }
        else
        {
            return scimitarStalll;
        }
    }
}