# Fix for Windows

**Follow these steps:**

* Create a windows regedit new DWORD
  * Press Windows Button + R, type “regedit”, and then click OK.
  * Navigate to the following registry subkey:
  * HKEY_LOCAL_MACHINE > SOFTWARE > Microsoft > Windows > CurrentVersion > SideBySide
  * Right-click, select NEW > DWORD (32 bit) Value
  * Type PreferExternalManifest, and then press ENTER.
  * Right-click PreferExternalManifest, and then click Modify.
  * Enter Value Data 1 and select Decimal.
  * Click OK.

* Create the two .manifest file (JDK)
  * Go to your java JDK installation folder and open the bin directory
  * Create a first file called java.exe.manifest (add the code at the end of this post).
  * Create a second one called javaw.exe.manifest (add the code at the end of this post).

* Create the two .manifest file (JRE)
  * Go to your java JRE installation folder and open the bin directory
  * Create a first file called java.exe.manifest (add the code at the end of this post).
  * Create a second one called javaw.exe.manifest (add the code at the end of this post).

* Restart your java application.

**Code to Paste into the `.manifest` files:**

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<assembly xmlns="urn:schemas-microsoft-com:asm.v1" manifestVersion="1.0" xmlns:asmv3="urn:schemas-microsoft-com:asm.v3">

<dependency>
  <dependentAssembly>
    <assemblyIdentity
      type="win32"
      name="Microsoft.Windows.Common-Controls"
      version="6.0.0.0" processorArchitecture="*"
      publicKeyToken="6595b64144ccf1df"
      language="*">
    </assemblyIdentity>
  </dependentAssembly>
</dependency>

<dependency>
  <dependentAssembly>
    <assemblyIdentity
      type="win32"
      name="Microsoft.VC90.CRT"
      version="9.0.21022.8"
      processorArchitecture="amd64"
      publicKeyToken="1fc8b3b9a1e18e3b">
    </assemblyIdentity>
  </dependentAssembly>
</dependency>

<trustInfo xmlns="urn:schemas-microsoft-com:asm.v3">
  <security>
    <requestedPrivileges>
      <requestedExecutionLevel
        level="asInvoker"
        uiAccess="false"/>
    </requestedPrivileges>
  </security>
</trustInfo>

<asmv3:application>
  <asmv3:windowsSettings xmlns="http://schemas.microsoft.com/SMI/2005/WindowsSettings">
    <ms_windowsSettings:dpiAware xmlns:ms_windowsSettings="http://schemas.microsoft.com/SMI/2005/WindowsSettings">false</ms_windowsSettings:dpiAware>
  </asmv3:windowsSettings>
</asmv3:application>

</assembly>
```

**Note:** This fix can be used for any program, not only Java.

* If you need to fix the DPI for a JNLP application launcher, you have to add the following key to the resources section inside the .jnlp file:
  
    ```xml
    <property name="sun.java2d.dpiaware" value="false"/>
    ```


## After Upgrading Windows

After Upgrading Windows, you should apply this fix again if it doesn't work anymore.



## Afer Updating Java

Afer Updating Java, you should copy and paste the `.manifest` files into new Java's directory.
