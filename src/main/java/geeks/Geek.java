package geeks;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

import java.util.List;

public class Geek {
  public String nom;
  public String prenom;
  public String pictureUrl;
  public String email;
  public String ville;
    public List<String> likes = Lists.newArrayList();

    public Result toResult() {
      String pictureUrl;
      if (this.pictureUrl != null) pictureUrl = this.pictureUrl;
      else if (email == null) pictureUrl = null;
      else pictureUrl = ("http://gravatar.com/avatar/" + Hashing.md5().hashBytes(email.getBytes()));
      return new Result(prenom, ville, like(0), like(1), like(2), pictureUrl);
  }

    public String like(int i) {
        return likes.size() > i  ? likes.get(i) : null;
    }

    public boolean matches(String search) {
    if (Strings.isNullOrEmpty(search)) {
      return false;
    }
    if (search.length() < 3) {
      return false;
    }
    return matchesAny(search, likes);
  }

  private static boolean matchesAny(String search, Iterable<String> terms) {
    String trimmedSearch = search.trim();

    for (String term : terms) {
      if (matches(trimmedSearch, Strings.nullToEmpty(term).trim())) {
        return true;
      }
    }

    return false;
  }

  private static boolean matches(String search, String term) {
    return search.equalsIgnoreCase(term)
      || plural(search).equalsIgnoreCase(term)
      || search.equalsIgnoreCase(term + "s");
  }

  private static String plural(String word) {
    return word + "s";
  }
}
