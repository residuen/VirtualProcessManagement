import javax.swing.JOptionPane;

import de.virtualprocessmanagement.gui.GuiBuilder;
import de.virtualprocessmanagement.tools.Dialog;

/*
OpenEdu-VirtualWarehouseManager: Grafische Darstellung von Warenhaus-Logistik-Ablaeufen

Copyright (C) 2010 Karsten Bettray

Dieses Programm ist freie Software. Sie koennen es unter den Bedingungen der GNU General Public License,
wie von der Free Software Foundation veroeffentlicht, weitergeben und/oder modifizieren, entweder gemaess
Version 3 der Lizenz oder (nach Ihrer Option) jeder spaeteren Version.
Die Veroeffentlichung dieses Programms erfolgt in der Hoffnung, dass es Ihnen von Nutzen sein wird, aber
OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FUER
EINEN BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm erhalten haben.
Falls nicht, siehe <http://www.gnu.org/licenses/>.
*/

public class VirtualProcessManagement
{

	public VirtualProcessManagement()
	{
		System.out.println("user.home="+System.getProperty("user.home"));
		System.out.println("CPU-Cores: "+ Runtime.getRuntime().availableProcessors() );
		
		new GuiBuilder();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{	
		new VirtualProcessManagement();
	}
}
