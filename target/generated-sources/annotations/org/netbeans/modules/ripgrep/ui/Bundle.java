package org.netbeans.modules.ripgrep.ui;
/** Localizable strings for {@link org.netbeans.modules.ripgrep.ui}. */
class Bundle {
    /**
     * @return <i>Hide</i>
     * @see HideResultAction
     */
    static String HideResultAction_displayName() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "HideResultAction.displayName");
    }
    /**
     * @return <i>More</i>
     * @see MoreAction
     */
    static String MoreAction_name() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MoreAction.name");
    }
    /**
     * @return <i>Refresh</i>
     * @see RefreshAction
     */
    static String RefreshAction_name() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "RefreshAction.name");
    }
    /**
     * @return <i>Select In</i>
     * @see SelectInAction
     */
    static String SelectInAction_name() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "SelectInAction.name");
    }
    /**
     * @return <i>Favorites</i>
     * @see SelectInAction
     */
    static String SelectIn_Favorites() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "SelectIn.Favorites");
    }
    /**
     * @return <i>Files</i>
     * @see SelectInAction
     */
    static String SelectIn_Files() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "SelectIn.Files");
    }
    /**
     * @return <i>Projects</i>
     * @see SelectInAction
     */
    static String SelectIn_Projects() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "SelectIn.Projects");
    }
    private Bundle() {}
}
