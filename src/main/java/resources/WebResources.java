package resources;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import restx.factory.Component;
import webserver.templating.ContentWithVariables;
import webserver.templating.Layout;
import webserver.templating.Template;
import webserver.templating.YamlFrontMatter;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class WebResources {

  protected String templatize(String body) {
    return templatize(body, ImmutableMap.of());
  }

  protected String templatize(String body, Map<?, ?> variables) {
    ContentWithVariables yaml = new YamlFrontMatter().parse(body);

    Map<String, String> yamlVariables = yaml.getVariables();
    String content = yaml.getContent();

    String layout = yamlVariables.get("layout");
    if (layout != null) {
        Optional<File> file = file(layout);
        if (file.isPresent()) {
            content = new Layout(read(file.get())).apply(content);
        }
    }

    return new Template().apply(content, ImmutableMap.builder().putAll(variables).putAll(yamlVariables).build());
  }

  protected String read(File file) {
    try {
      return Files.toString(file, Charsets.UTF_8);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  protected Optional<File> file(String parent, String path) {
    return file(new File(parent, path).getPath());
  }

  protected Optional<File> file(String path) {
    if (!exists(path)) {
      return Optional.absent();
    }
    return Optional.of(new File("web", path));
  }

  protected boolean exists(String path) {
    if (path.endsWith("/")) {
      return false;
    }

    try {
      File root = new File("web");
      File file = new File(root, path);
      if (!file.exists() || !file.getCanonicalPath().startsWith(root.getCanonicalPath())) {
        return false;
      }

      return true;
    } catch (IOException e) {
      return false;
    }
  }
}
