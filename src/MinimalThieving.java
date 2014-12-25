import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.events.MessageEvent;
import org.rev317.min.api.events.listeners.MessageListener;
import org.rev317.min.api.methods.Players;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

@ScriptManifest(author = "Minimal",
        category = Category.THIEVING,
        description = "Steals from the stalls at ::home in Ikov.",
        name = "MinimalThieving",
        servers = { "Ikov" },
        version = 1.0)

public class MinimalThieving extends Script implements Paintable, MessageListener
{
    private final ArrayList<Strategy> strategies = new ArrayList<>();

    private Timer timer;

    private Image image = getImage("http://i.imgur.com/3WsDK8O.png");

    public static String status = "";

    private boolean showPaint = false;

    private int moneyGained;
    private int steals;

    public Image getImage(String str)
    {
        try
        {
            return ImageIO.read(new URL(str));
        }
        catch(IOException e)
        {
            return null;
        }
    }

    @Override
    public boolean onExecute()
    {
        showPaint = true;

        timer = new Timer();

        strategies.add(new Sell());
        strategies.add(new Wait());
        strategies.add(new Steal());
        provide(strategies);
        return true;
    }

    @Override
    public void onFinish()
    {
        System.out.println("MinimalThieving ran for: " + timer.toString());
        System.out.println("Money(hr): " + getMoney(moneyGained));
        System.out.println("Steals(hr): " + getSteals(steals));
    }

    @Override
    public void paint(Graphics g)
    {
        if (showPaint)
        {
            g.drawImage(image, 548, 203, null);
        }


        g.setFont(new Font("Helvetica", Font.PLAIN, 14));
        g.setColor(Color.BLACK);

        g.drawString("Time: " + timer.toString(), 560, 266);

        g.drawString("Money(hr): " + getMoney(moneyGained), 560, 316);

        g.drawString("Steals(hr): " + getSteals(steals), 560, 367);

        g.drawString(status, 15, 15);
    }

    public String getMoney(int moneyGained)
    {
        int hourlyMoney = timer.getPerHour(moneyGained);

        return formatNumber(moneyGained) + "(" + formatNumber(hourlyMoney) + ")";
    }

    public String getSteals(int steals)
    {
        int hourlySteals = timer.getPerHour(steals);

        return formatNumber(steals) + "(" + formatNumber(hourlySteals) + ")";
    }

    public String formatNumber(double number)
    {
        DecimalFormat compact = new DecimalFormat("#,###.0");

        if (number >= 1000000)
        {
            return compact.format(number / 1000000) + "M";
        }
        else if (number >= 1000
                && number < 1000000)
        {
            return compact.format(number / 1000) + "K";
        }

        return "" + number;
    }

    @Override
    public void messageReceived(MessageEvent m)
    {
        if (m.getType() == 0)
        {
            if (m.getMessage().contains("silk"))
            {
                moneyGained += 5120;
                steals++;
            }

            if (m.getMessage().contains("golden ring"))
            {
                moneyGained += 80;
                steals++;
            }

            if (m.getMessage().contains("emerald ring"))
            {
                moneyGained += 12000;
                steals++;
            }

            if (m.getMessage().contains("battlestaff"))
            {
                moneyGained += 16000;
                steals++;
            }

            if (m.getMessage().contains("adamant scimitar"))
            {
                moneyGained += 20000;
                steals++;
            }

            if (m.getMessage().contains("command does not exist"))
            {
                if (showPaint)
                {
                    showPaint = false;
                }
                else
                {
                    showPaint = true;
                }
            }
        }
    }
}