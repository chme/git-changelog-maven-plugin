package se.bjurr.gitchangelog.plugin;

import static org.apache.maven.plugins.annotations.LifecyclePhase.PROCESS_SOURCES;
import static se.bjurr.gitchangelog.api.GitChangelogApi.gitChangelogApiBuilder;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import se.bjurr.gitchangelog.api.GitChangelogApi;

@Mojo(name = "git-changelog", defaultPhase = PROCESS_SOURCES, threadSafe = true)
public class GitChangelogMojo extends AbstractMojo {
  private static final String DEFAULT_FILE = "CHANGELOG.md";

  @Parameter(property = "toRef", required = false)
  private String toRef;

  @Parameter(property = "toCommit", required = false)
  private String toCommit;

  @Parameter(property = "fromRef", required = false)
  private String fromRef;

  @Parameter(property = "fromCommit", required = false)
  private String fromCommit;

  @Parameter(property = "settingsFile", required = false)
  private String settingsFile;

  @Parameter(property = "extendedVariables", required = false)
  private Map extendedVariables;

  @Parameter(property = "extendedHeaders", required = false)
  private Map extendedHeaders;

  // map variables cannot be passed through maven cli use this property as a workaround
  @Parameter(property = "extendedVariablesCli", required = false)
  private String[] extendedVariablesCli;

  @Parameter(property = "templateFile", required = false)
  private String templateFile;

  @Parameter(property = "templateContent", required = false)
  private String templateContent;

  @Parameter(property = "file", required = false)
  private File file;

  @Parameter(property = "readableTagName", required = false)
  private String readableTagName;

  @Parameter(property = "ignoreTagsIfNameMatches", required = false)
  private String ignoreTagsIfNameMatches;

  @Parameter(property = "dateFormat", required = false)
  private String dateFormat;

  @Parameter(property = "timeZone", required = false)
  private String timeZone;

  @Parameter(property = "removeIssueFromMessage", required = false)
  private boolean removeIssueFromMessage;

  @Parameter(property = "ignoreCommitsIfMessageMatches", required = false)
  private String ignoreCommitsIfMessageMatches;

  @Parameter(property = "ignoreCommitsOlderThan", required = false)
  private Date ignoreCommitsOlderThan;

  @Parameter(property = "untaggedName", required = false)
  private String untaggedName;

  @Parameter(property = "noIssueName", required = false)
  private String noIssueName;

  @Parameter(property = "gitHubApi", required = false)
  private String gitHubApi;

  @Parameter(property = "gitHubApiIssuePattern", required = false)
  private String gitHubApiIssuePattern;

  @Parameter(property = "gitHubToken", required = false)
  private String gitHubToken;

  @Parameter(property = "gitHubIssuePattern", required = false)
  private String gitHubIssuePattern;

  @Parameter(property = "gitLabServer", required = false)
  private String gitLabServer;

  @Parameter(property = "gitLabProjectName", required = false)
  private String gitLabProjectName;

  @Parameter(property = "gitLabToken", required = false)
  private String gitLabToken;

  @Parameter(property = "jiraIssuePattern", required = false)
  private String jiraIssuePattern;

  @Parameter(property = "jiraPassword", required = false)
  private String jiraPassword;

  @Parameter(property = "jiraServer", required = false)
  private String jiraServer;

  @Parameter(property = "jiraUsername", required = false)
  private String jiraUsername;

  @Parameter(property = "redmineIssuePattern", required = false)
  private String redmineIssuePattern;

  @Parameter(property = "redminePassword", required = false)
  private String redminePassword;

  @Parameter(property = "redmineServer", required = false)
  private String redmineServer;

  @Parameter(property = "redmineUsername", required = false)
  private String redmineUsername;

  @Parameter(property = "redmineToken", required = false)
  private String redmineToken;

  @Parameter(property = "ignoreCommitsWithoutIssue", required = false)
  private Boolean ignoreCommitsWithoutIssue;

  @Parameter(property = "customIssues", required = false)
  private List<CustomIssue> customIssues;

  @Parameter(property = "skip", required = false)
  private Boolean skip;

  @Parameter(property = "pathFilter", required = false)
  private String pathFilter;

  @Parameter(property = "javascriptHelper", required = false)
  private String javascriptHelper;

  @Override
  public void execute() throws MojoExecutionException {
    if (this.skip != null && this.skip == true) {
      this.getLog().info("Skipping changelog generation");
      return;
    }
    try {
      final Map<String, String> extendedVariablesCliAsMap = this.convertExtendedVariablesCli2Map();
      this.extendedVariables.putAll(extendedVariablesCliAsMap);

      GitChangelogApi builder;
      builder = gitChangelogApiBuilder();

      if (this.isSupplied(this.javascriptHelper)) {
        builder //
            .withHandlebarsHelper(this.javascriptHelper);
      }

      if (this.isSupplied(this.settingsFile)) {
        builder.withSettings(new File(this.settingsFile).toURI().toURL());
      }

      if (this.isSupplied(this.extendedVariables)) {
        builder.withExtendedVariables(this.extendedVariables);
      }

      if (this.isSupplied(this.extendedHeaders)) {
        builder.withExtendedHeaders(this.extendedHeaders);
      }

      if (this.isSupplied(this.toRef)) {
        builder.withToRef(this.toRef);
      }

      if (this.isSupplied(this.templateFile)) {
        builder.withTemplatePath(this.templateFile);
      }
      if (this.isSupplied(this.templateContent)) {
        builder.withTemplateContent(this.templateContent);
      }
      if (this.isSupplied(this.fromCommit)) {
        builder.withFromCommit(this.fromCommit);
      }
      if (this.isSupplied(this.fromRef)) {
        builder.withFromRef(this.fromRef);
      }
      if (this.isSupplied(this.toCommit)) {
        builder.withToCommit(this.toCommit);
      }

      if (this.isSupplied(this.ignoreTagsIfNameMatches)) {
        builder.withIgnoreTagsIfNameMatches(this.ignoreTagsIfNameMatches);
      }
      if (this.isSupplied(this.readableTagName)) {
        builder.withReadableTagName(this.readableTagName);
      }
      if (this.isSupplied(this.dateFormat)) {
        builder.withDateFormat(this.dateFormat);
      }
      if (this.isSupplied(this.timeZone)) {
        builder.withTimeZone(this.timeZone);
      }
      builder.withRemoveIssueFromMessageArgument(this.removeIssueFromMessage);
      if (this.isSupplied(this.ignoreCommitsIfMessageMatches)) {
        builder.withIgnoreCommitsWithMessage(this.ignoreCommitsIfMessageMatches);
      }
      if (this.ignoreCommitsOlderThan != null) {
        builder.withIgnoreCommitsOlderThan(this.ignoreCommitsOlderThan);
      }
      if (this.isSupplied(this.untaggedName)) {
        builder.withUntaggedName(this.untaggedName);
      }
      if (this.isSupplied(this.noIssueName)) {
        builder.withNoIssueName(this.noIssueName);
      }
      if (this.ignoreCommitsWithoutIssue != null) {
        builder.withIgnoreCommitsWithoutIssue(this.ignoreCommitsWithoutIssue);
      }
      for (final CustomIssue customIssue : this.customIssues) {
        builder.withCustomIssue(
            customIssue.getName(),
            customIssue.getPattern(),
            customIssue.getLink(),
            customIssue.getTitle());
      }
      if (this.isSupplied(this.gitHubApi)) {
        builder.withGitHubApi(this.gitHubApi);
      }
      if (this.isSupplied(this.gitHubToken)) {
        builder.withGitHubToken(this.gitHubToken);
      }
      if (this.isSupplied(this.gitHubIssuePattern)) {
        builder.withGitHubIssuePattern(this.gitHubIssuePattern);
      }

      if (this.isSupplied(this.gitLabProjectName)) {
        builder.withGitLabProjectName(this.gitLabProjectName);
      }
      if (this.isSupplied(this.gitLabServer)) {
        builder.withGitLabServer(this.gitLabServer);
      }
      if (this.isSupplied(this.gitLabToken)) {
        builder.withGitLabToken(this.gitLabToken);
      }

      if (this.isSupplied(this.jiraUsername)) {
        builder.withJiraUsername(this.jiraUsername);
      }
      if (this.isSupplied(this.jiraPassword)) {
        builder.withJiraPassword(this.jiraPassword);
      }
      if (this.isSupplied(this.jiraIssuePattern)) {
        builder.withJiraIssuePattern(this.jiraIssuePattern);
      }
      if (this.isSupplied(this.jiraServer)) {
        builder.withJiraServer(this.jiraServer);
      }

      if (this.isSupplied(this.redmineUsername)) {
        builder.withRedmineUsername(this.redmineUsername);
      }
      if (this.isSupplied(this.redminePassword)) {
        builder.withRedminePassword(this.redminePassword);
      }
      if (this.isSupplied(this.redmineToken)) {
        builder.withRedmineToken(this.redmineToken);
      }
      if (this.isSupplied(this.redmineIssuePattern)) {
        builder.withRedmineIssuePattern(this.redmineIssuePattern);
      }
      if (this.isSupplied(this.redmineServer)) {
        builder.withRedmineServer(this.redmineServer);
      }

      if (this.isSupplied(this.pathFilter)) {
        builder.withPathFilter(this.pathFilter);
      }

      if (this.file == null) {
        this.getLog().info("No output set, using file " + DEFAULT_FILE);
        this.file = new File(DEFAULT_FILE);
      }

      if (this.file != null) {
        builder.toFile(this.file);
        this.getLog().info("#");
        this.getLog().info("# Wrote: " + this.file);
        this.getLog().info("#");
      }

    } catch (final Exception e) {
      this.getLog().error("GitChangelog", e);
    }
  }

  private boolean isSupplied(final String param) {
    return param != null && !param.isEmpty();
  }

  private boolean isSupplied(final Map<?, ?> parameter) {
    return parameter != null && !parameter.isEmpty();
  }

  private Map<String, String> convertExtendedVariablesCli2Map() {
    final Map<String, String> map = new HashMap<>();
    if (this.extendedVariablesCli != null) {
      this.getLog().info("Extended variables:");
      for (final String entry : this.extendedVariablesCli) {
        final int equalsPosition = entry.indexOf("=");
        final String variable = entry.substring(0, equalsPosition);
        final String value = entry.substring(equalsPosition + 1);
        this.getLog().info(variable + " = " + value);
        map.put(variable, value);
      }
    }
    return map;
  }
}
