package org.netbeans.modules.ripgrep;
/** Localizable strings for {@link org.netbeans.modules.ripgrep}. */
class Bundle {
    /**
     * @return <i>Apply to all big files</i>
     * @see ContextView
     */
    static String LBL_ContextView_ApplyAll() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_ContextView_ApplyAll");
    }
    /**
     * @return <i>Show</i>
     * @see ContextView
     */
    static String LBL_ContextView_Show() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_ContextView_Show");
    }
    /**
     * @return <i>Do Not Show</i>
     * @see ContextView
     */
    static String LBL_ContextView_Skip() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_ContextView_Skip");
    }
    /**
     * @return <i>Line endin&amp;g: </i>
     * @see PatternSandbox
     */
    static String LBL_LineEnding() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_LineEnding");
    }
    /**
     * @return <i>Line ending sequence</i>
     * @see PatternSandbox
     */
    static String LBL_LineEnding_accName() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_LineEnding.accName");
    }
    /**
     * @return <i>Line ending sequence that is used in the text pane</i>
     * @see PatternSandbox
     */
    static String LBL_LineEnding_tooltip() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_LineEnding.tooltip");
    }
    /**
     * @return <i>\r: Old Mac</i>
     * @see PatternSandbox
     */
    static String LBL_MacOld() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_MacOld");
    }
    /**
     * @param number_of_files_Please_do_not_translate_choice_number_and_integer_ number of files. Please do not translate "choice", "#", "number" and "integer".
     * @return <i>Open {0,choice,0#Documents|1#Document|1&lt;Documents} ({0,choice,0#0 files|1#1 file|1&lt;{0,number,integer} files})</i>
     * @see OpenFilesSearchScopeProvider
     */
    static String LBL_OpenFileScope(Object number_of_files_Please_do_not_translate_choice_number_and_integer_) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_OpenFileScope", number_of_files_Please_do_not_translate_choice_number_and_integer_);
    }
    /**
     * @return <i>\n: Unix (Linux, Mac)</i>
     * @see PatternSandbox
     */
    static String LBL_Unix() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_Unix");
    }
    /**
     * @return <i>\r\n: Windows</i>
     * @see PatternSandbox
     */
    static String LBL_Windows() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "LBL_Windows");
    }
    /**
     * @return <i>The text match cannot be shown.</i>
     * @see TextDetail
     */
    static String MSG_CannotShowTextDetai() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MSG_CannotShowTextDetai");
    }
    /**
     * @return <i>File is too big</i>
     * @see ContextView
     */
    static String MSG_ContextView_fileTooBig() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MSG_ContextView_fileTooBig");
    }
    /**
     * @param file_name file name
     * @param file_size_in_kilobytes file size in kilobytes
     * @return <i>File </i>{@code file_name}<i> is quite big (</i>{@code file_size_in_kilobytes}<i> kB).<br>Showing it can cause memory and performance problems.<br>Do you want to show content of this file?</i>
     * @see ContextView
     */
    static String MSG_ContextView_showBigFile(Object file_name, Object file_size_in_kilobytes) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MSG_ContextView_showBigFile", file_name, file_size_in_kilobytes);
    }
    /**
     * @param file_path file path
     * @return <i>File </i>{@code file_path}<i> does not exist.</i>
     * @see SearchScopeBrowse
     */
    static String MSG_FileDoesNotExist(Object file_path) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MSG_FileDoesNotExist", file_path);
    }
    /**
     * @return <i>Pattern Matching took too long and was cancelled.</i>
     * @see PatternSandbox
     */
    static String MSG_PatternSansboxTimout() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MSG_PatternSansboxTimout");
    }
    /**
     * @param Maximal_number_of_characters_in_search_field Maximal number of characters in search field
     * @return <i>The text cannot be pasted into search field. Limit is </i>{@code Maximal_number_of_characters_in_search_field}<i> characters.</i>
     * @see BasicSearchForm
     */
    static String MSG_TextTooLong(Object Maximal_number_of_characters_in_search_field) {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "MSG_TextTooLong", Maximal_number_of_characters_in_search_field);
    }
    /**
     * @return <i>Show Big File?</i>
     * @see ContextView
     */
    static String TTL_ContextView_showBigFile() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "TTL_ContextView_showBigFile");
    }
    /**
     * @return <i>File Error</i>
     * @see SearchScopeBrowse
     */
    static String TTL_FileDoesNotExist() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "TTL_FileDoesNotExist");
    }
    private Bundle() {}
}
