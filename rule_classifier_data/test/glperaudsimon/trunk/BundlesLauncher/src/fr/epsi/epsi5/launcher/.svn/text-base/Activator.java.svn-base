package fr.epsi.epsi5.launcher;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;


/**
 * Activator du BundlesLauncher.
 * @author Wilhelm Peraud-Loïc Simon
 */
public class Activator implements BundleActivator {
	/**
	 * Méthode start de l'Activator.
	 * Cette méthode démarre tous les autres bundles
	 * @param context Contexte du bundle
	 * @throws Exception Exception pouvant avoir lieu lors du lancement du bundle
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@SuppressWarnings("unchecked")
	public void start(BundleContext context) throws Exception {
		// Liste des bundles du framework
		Bundle[] bundles = context.getBundles();
		
		//Id du LauncherBundle
		long launcherBundleId = context.getBundle().getBundleId();
				
		//Pour chaque bundle, on va le lancer
		for (Bundle bundle : bundles) {
			//Entêtes des bundles
			Dictionary headers = bundle.getHeaders();
			//Si le bundle n'est pas lancé, qu'il ne s'agit pas d'un fragment et que son id
			//est différent du LauncherBundle alors on va tenter de le lancer
			if((bundle.getState() == Bundle.RESOLVED) 
				&& (headers.get(Constants.FRAGMENT_HOST) == null)
				&& (bundle.getBundleId() != launcherBundleId)) { 

				bundle.start();
			}
		}
	}

	/**
	 * Méthode stop de l'activator.
	 * Désactive tous les bundles en même temps que ce bundle.
	 * @param context Contexte du bundle
	 * @throws Exception Exception pouvant avoir lieu lors de l'arrêt du bundle
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		Bundle[] bundles = context.getBundles();
		long launcherBundleId = context.getBundle().getBundleId();
		for (Bundle bundle : bundles) {
			if((bundle.getState() == Bundle.ACTIVE) && (bundle.getHeaders().get(Constants.FRAGMENT_HOST) == null)
					&& (bundle.getBundleId() != launcherBundleId)) {
				bundle.stop();
			}
		}
	}

}
