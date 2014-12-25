import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.Npc;

import java.util.ArrayList;
import java.util.Stack;

public class Sell implements Strategy
{
    private Npc banditLeader;

    private int[] stolenItems = { 951, 1636, 1640, 1332 };
    private int banditLeaderId = 1878;

    @Override
    public boolean activate()
    {
        if (Inventory.getCount() >= 27)
        {
            for (Npc n : Npcs.getNearest(banditLeaderId))
            {
                banditLeader = n;

                return true;
            }
        }

        return false;
    }

    @Override
    public void execute()
    {
        if (banditLeader != null)
        {
            if (Inventory.isFull())
            {
                MinimalThieving.status = "Making room in inventory";

                Inventory.getItems(stolenItems)[0].drop();

                Time.sleep(new SleepCondition()
                {
                    @Override
                    public boolean isValid()
                    {
                        return !Inventory.isFull();
                    }
                }, 1500);
            }

            if (Game.getOpenInterfaceId() != 3824)
            {
                banditLeader.interact(0);

                Time.sleep(new SleepCondition()
                {
                    @Override
                    public boolean isValid()
                    {
                        return Game.getOpenInterfaceId() == 3824;
                    }
                }, 5000);
            }

            if (Game.getOpenInterfaceId() == 3824)
            {
                sellAllExcept(996);

                Time.sleep(new SleepCondition()
                {
                    @Override
                    public boolean isValid()
                    {
                        return Inventory.getCount() == 1;
                    }
                }, 1500);
            }
        }
    }

    /**
     * Deposits all items except the ones we specify in the parameter
     * @param ids the ids of the items we don't want to deposit
     */
    public void sellAllExcept(int... ids)
    {
        ArrayList<Integer> ignored = new ArrayList<>();
        Stack<Integer> itemsToDeposit = new Stack<>();

        for (int i : ids)
        {
            ignored.add(i);
        }

        for (Item i : Inventory.getItems())
        {
            if (!ignored.contains(i.getId())
                    && !itemsToDeposit.contains(i.getId()))
            {
                itemsToDeposit.push(i.getId());
            }
        }

        while (!itemsToDeposit.isEmpty())
        {
            int itemId = itemsToDeposit.pop();

            if (Inventory.getItems(itemId).length > 0)
            {
                Item item = Inventory.getItems(itemId)[Inventory.getItems(itemId).length - 1];

                double length = Inventory.getItems(itemId).length;

                int j;

                if (length / 10 < 1.1)
                {
                    j = 1;
                }
                else if (length / 10 < 2.1)
                {
                    j = 2;
                }
                else
                {
                    j = 3;
                }

                for (int i = 0; i < j; i++)
                {
                    Menu.sendAction(431, item.getId() - 1, item.getSlot(), 3823);
                    Time.sleep(500);
                }
            }
        }
    }
}