package org.netbeans.modules.ripgrep.matcher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.netbeans.api.queries.FileEncodingQuery;
import org.netbeans.api.search.SearchPattern;
import org.netbeans.api.search.provider.SearchListener;
import org.netbeans.modules.ripgrep.MatchingObject;
import org.netbeans.modules.ripgrep.TextDetail;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;

public class RipGrepMatcher extends AbstractMatcher {

    private static final Logger LOG = Logger.getLogger(RipGrepMatcher.class.getName());

    private static final String RG_COMMAND;

    // Search parameters
    private String searchTerm;
    private boolean isCaseSensitive = false;
    private boolean isWholeWord = false;
    private boolean isLiteral = false;
    private boolean useRegex = true;
    private boolean usePcre = true;
    private List<String> scopeFiles = new ArrayList<>();
    private boolean searchInArchives = false;
    private boolean searchInGeneratedSources = false;
    private boolean isGlobeInclude = false;
    private boolean isUseIgnoreList = false;
    private String fileNamePatterns;
    private List<String> includes = new ArrayList<>();

    private Process process;
    private final SearchPattern searchPattern;

    static {

        File bins = InstalledFileLocator.getDefault().locate(
                "ripgrep/bins",
                "org.netbeans.modules.ripgrep",
                false);

        Collection<File> listFiles = FileUtils.listFiles(bins, new NameFileFilter(new String[]{"rg", "rg.exe"}),
                new DirectoryFileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() && StringUtils.equalsAny(file.getName(), "windows", "linux", "mac");
            }

        });
        switch (Utilities.getOperatingSystem()) {
            case Utilities.OS_LINUX:
                RG_COMMAND = listFiles.stream()
                        .filter(file -> file.getParentFile().getName().equals("linux"))
                        .map(f -> {
                            f.setExecutable(true, true);
                            return f;
                        }).findFirst().get().getAbsolutePath();
                break;

            case Utilities.OS_WIN_OTHER:
                RG_COMMAND = listFiles.stream()
                        .filter(file -> file.getParentFile().getName().equals("windows"))
                        .map(f -> {
                            f.setExecutable(true, true);
                            return f;
                        }).findFirst().get().getAbsolutePath();
                break;
            case Utilities.OS_MAC:
                RG_COMMAND = listFiles.stream()
                        .filter(file -> file.getParentFile().getName().equals("ios"))
                        .map(f -> {
                            f.setExecutable(true, true);
                            return f;
                        }).findFirst().get().getAbsolutePath();
                break;
            default:
                LOG.warning("Couldn't find provided Ripgrep binaries. Swithing to default installed 'rg' command");
                RG_COMMAND = "rg";
        }

    }

    public RipGrepMatcher(SearchPattern searchPattern) {
        this.searchPattern = searchPattern;

    }

    public RipGrepMatcher setLiteral(boolean literal) {
        this.isLiteral = literal;
        this.useRegex = !literal; // Disable regex when using literal
        return this;
    }

    public RipGrepMatcher setUseRegex(boolean useRegex) {
        this.useRegex = useRegex;
        return this;
    }

    public RipGrepMatcher setUsePcre(boolean usePcre) {
        this.usePcre = usePcre;
        return this;
    }

    public RipGrepMatcher setUseIgnoreList(boolean useIgnoreList) {
        this.isUseIgnoreList = useIgnoreList;
        return this;
    }

    public RipGrepMatcher setCaseSensitive(boolean isCaseSensitive) {
        this.isCaseSensitive = isCaseSensitive;
        return this;
    }

    public RipGrepMatcher setGlobeInclude(boolean isGlobeInclude) {
        this.isGlobeInclude = isGlobeInclude;
        return this;
    }

    public RipGrepMatcher setWholeWord(boolean isWholeWord) {
        this.isWholeWord = isWholeWord;
        return this;
    }

    public RipGrepMatcher setSearchInArchives(boolean searchInArchives) {
        this.searchInArchives = searchInArchives;
        return this;
    }

    public RipGrepMatcher setSearchInGeneratedSources(boolean searchInGeneratedSources) {
        this.searchInGeneratedSources = searchInGeneratedSources;
        return this;
    }

    public RipGrepMatcher addIncludes(List<String> patterns) {
        this.includes = patterns;
        return this;
    }

    public RipGrepMatcher addScopeFiles(List<String> filesPath) {
        this.scopeFiles = filesPath;
        return this;
    }

    public RipGrepMatcher setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
        return this;
    }

    public RipGrepMatcher setFileNamePattern(String fileNamePattern) {
        this.fileNamePatterns = fileNamePattern;
        return this;
    }

    // Builds the Ripgrep command and runs it
    public Process getProcess() {
        try {
            List<String> command = buildCommand();

            LOG.info(command.toString());
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true); // Merge stdout and stderr
            process = processBuilder.start();

            return process;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    // Build the command with the provided options
    private List<String> buildCommand() {
        List<String> command = new ArrayList<>();

        command.add(RG_COMMAND);

        if (searchTerm.isEmpty() && !fileNamePatterns.isEmpty()) {
            command.add("--files");
        } else if (isLiteral) {
            command.add("--fixed-strings");
        } else if (useRegex) {
            command.add("--regexp");
        }
        command.add(searchTerm);

        // Search in archives
        if (searchInArchives) {
            command.add("--search-zip");
        }

        if (isCaseSensitive) {
            command.add("--case-sensitive");
        } else {
            command.add("--ignore-case");
        }

        if (isWholeWord) {
            command.add("--word-regexp");
        }

        // Include patterns
        if (!fileNamePatterns.isEmpty() && !isGlobeInclude) {

            for (String include : includes) {
                command.add("--glob");
                command.add(include);
            }
        }

        if (isGlobeInclude) {
            command.add("--glob");
            command.add(fileNamePatterns);
        }

        // Exclude patterns
        if (isUseIgnoreList) {
        }

        // extra options
        command.add("--json");

        if (searchInGeneratedSources) {
            command.add("--no-ignore");
            command.add("--hidden");
        }

        if (usePcre) {
            command.add("--engine");
            command.add("pcre2");
        }

        // multiline search
        if (searchTerm.matches(".*\\n.*")) {
            command.add("--multiline");
            // command.removeIf((c) -> c.equals("--fixed-strings"));
        }

        // Set the directory to getProcess in
        for (String scopeFile : scopeFiles) {
            command.add(scopeFile);
        }

        return command;
    }

    public BufferedReader getReader() {
        // Read the output from the process
        return new BufferedReader(new InputStreamReader(this.getProcess().getInputStream()));
    }

    @Override
    protected MatchingObject.Def checkMeasuredInternal(FileObject file, SearchListener listener) {

        return new MatchingObject.Def(file, FileEncodingQuery.getEncoding(file), new ArrayList<>());

    }

    public List<TextDetail> getDetails(FileObject fo, RipGrepOutput.Data data) {
        List<TextDetail> textDetails = new ArrayList<>();

        for (RipGrepMatcher.RipGrepOutput.Submatche submatche : data.getSubmatches()) {

            try {
                TextDetail textDetail = new TextDetail(DataObject.find(fo),
                        searchPattern);

                textDetail.setLine(data.getLineNumber());// show matching line number in found
                textDetail.setColumn(submatche.getStart() + 1);// show matching column number in found
                textDetail.setMatchedText(submatche.getMatch().getText());
                textDetail.setStartOffset(data.getAbsoluteOffset() + submatche.getStart());
                textDetail.setEndOffset(data.getAbsoluteOffset() + submatche.getEnd());
                textDetail.setMarkLength(submatche.getMatch().getText().length());// highlight from coulumn. Need the rp
                // highlight lenght
                textDetail.setLineText(data.getLines().getText());// show matching line text in found

                textDetails.add(textDetail);
                return textDetails;
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return null;
    }

    @Override
    public void terminate() {
        if (process != null) {
            process.destroy();
        }
    }

    // Class to represent output line
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RipGrepOutput {

        private final String type;
        private final Data data;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public RipGrepOutput(@JsonProperty("type") String type, @JsonProperty("data") Data data) {
            this.type = type;
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public Data getData() {
            return data;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Data {

            private final Path path;
            private final Lines lines;

            private final int lineNumber;
            private final int absoluteOffset;
            private final List<Submatche> submatches;

            @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
            public Data(@JsonProperty("path") Path path,
                    @JsonProperty("lines") Lines lines,
                    @JsonProperty("line_number") int lineNumber,
                    @JsonProperty("absolute_offset") int absoluteOffset,
                    @JsonProperty("submatches") List<Submatche> submatches) {
                this.path = path;
                this.lines = lines;
                this.lineNumber = lineNumber;
                this.absoluteOffset = absoluteOffset;
                this.submatches = submatches;
            }

            public Path getPath() {
                return path;
            }

            public Lines getLines() {
                return lines;
            }

            public int getLineNumber() {
                return lineNumber;
            }

            public int getAbsoluteOffset() {
                return absoluteOffset;
            }

            public List<Submatche> getSubmatches() {
                return submatches;
            }

        }

        public static class Path {

            private final String text;

            @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
            public Path(@JsonProperty("text") String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }

        }

        public static class Lines {

            private final String text;

            @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
            public Lines(@JsonProperty("text") String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }

        }

        public static class Submatche {

            private final Match match;
            private final int start;
            private final int end;

            @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
            public Submatche(@JsonProperty("match") Match match,
                    @JsonProperty("start") int start,
                    @JsonProperty("end") int end) {
                this.match = match;
                this.start = start;
                this.end = end;
            }

            public Match getMatch() {
                return match;
            }

            public int getStart() {
                return start;
            }

            public int getEnd() {
                return end;
            }

        }

        public static class Match {

            private final String text;

            @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
            public Match(@JsonProperty("text") String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }

        }

    }

}
