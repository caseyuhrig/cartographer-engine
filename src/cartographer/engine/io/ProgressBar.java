package cartographer.engine.io;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;


public class ProgressBar extends JProgressBar
{
    private static final long serialVersionUID = 1L;

    public void updateMaximum(final Integer max)
    {
        SwingUtilities.invokeLater(() -> {
            setMinimum(0);
            setMaximum(max);
            setValue(0);
        });
    }


    public void updatePosition(final Integer position)
    {
        SwingUtilities.invokeLater(() -> setValue(position));
    }
}
