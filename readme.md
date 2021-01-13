# Deploy
mvn clean deploy

# settings.xml config
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username></username>
      <password></password>
    </server>
  </servers>
  <profiles>
    <profile>
      <id>ossrh</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <gpg.executable>gpg</gpg.executable>
        <gpg.passphrase></gpg.passphrase>
      </properties>
    </profile>
  </profiles>
</settings>

# Mac settings.xml location
/usr/local/Cellar/maven/VER/libexec/conf/settings.xml


# Public Key Sharing
gpg --keyserver hkp://pool.sks-keyservers.net --send-keys KEY_ID
gpg --keyserver hkp://keys.openpgp.org --send-keys KEY_ID
gpg --keyserver hkp://keys.gnupg.net --send-keys KEY_ID
gpg --keyserver hkp://pool.sks-keyservers.net --send-keys KEY_ID

gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys KEY_ID
gpg --keyserver hkp://keys.openpgp.org --recv-keys KEY_ID
gpg --keyserver hkp://keys.gnupg.net --recv-keys KEY_ID
gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys KEY_ID
