$dir = "c:\Users\mauricio.rgobato\Documents\NexusDev\src\main\java\Janelas"
$files = Get-ChildItem -Path $dir -Filter "*.java"

$utf8NoBom = New-Object System.Text.UTF8Encoding($false)

foreach ($file in $files) {
    if ($file.Name -eq "CadastroMedicamento.java") { continue }
    $content = [System.IO.File]::ReadAllText($file.FullName)
    $modified = $false
    
    $regexLoop = [regex]::new('(?s)(btn\.setCursor[^;]+;\s*\})')
    if ($regexLoop.IsMatch($content)) {
        $loopMatch = $regexLoop.Match($content)
        $insertionPoint = $loopMatch.Index + $loopMatch.Length
        
        $btnLines = ""
        $btnMatches = [regex]::Matches($content, '\b(jB[A-Z][a-zA-Z0-9_]*)\b')
        $uniqueBtns = $btnMatches | Select-Object -ExpandProperty Value -Unique
        
        foreach ($btn in $uniqueBtns) {
            if ($btn -match "ActionPerformed") { continue }
            if ($content -match "$btn\.setBackground\(new java\.awt\.Color" -or $content -match "$btn\.setBackground\(new Color") { continue }
            
            if ($content -match "private javax\.swing\.JButton $btn;") {
                $color = ""
                if ($btn -match "^jB(Cadastrar|Finalizar|Confirmar|Adicionar|Reativar)") {
                    $color = "new java.awt.Color(0, 100, 0)"
                } elseif ($btn -match "^jB(Alterar|Atualizar)") {
                    $color = "new java.awt.Color(0, 120, 215)"
                } elseif ($btn -match "^jB(Excluir|Cancelar)") {
                    $color = "new java.awt.Color(150, 0, 0)"
                } elseif ($btn -match "^jB(Desativados|Inativos)") {
                    $color = "new java.awt.Color(100, 100, 0)"
                }
                
                if ($color -ne "") {
                    $btnLines += "`r`n        $btn.setBackground($color);"
                }
            }
        }
        
        if ($btnLines -ne "") {
            $content = $content.Insert($insertionPoint, $btnLines)
            $modified = $true
        }
    }
    
    $regexTable = [regex]::new('(?s)(\w+)\.getTableHeader\(\)\.setFont\([^;]+;')
    if ($regexTable.IsMatch($content)) {
        $matches = $regexTable.Matches($content)
        foreach ($match in $matches) {
            $tableName = $match.Groups[1].Value
            $origLine = $match.Value
            
            if ($content -notmatch "$tableName\.setSelectionBackground") {
                $newLines = "`r`n        $tableName.setSelectionBackground(new java.awt.Color(0, 100, 0, 80));`r`n        $tableName.setSelectionForeground(java.awt.Color.WHITE);`r`n        $tableName.getTableHeader().setBackground(new java.awt.Color(30, 30, 30));`r`n        $tableName.getTableHeader().setForeground(java.awt.Color.WHITE);`r`n        $origLine"
                $content = $content.Replace($origLine, $newLines)
                $modified = $true
            }
        }
    }
    
    if ($modified) {
        [System.IO.File]::WriteAllText($file.FullName, $content, $utf8NoBom)
        Write-Host "Updated $($file.Name)"
    }
}
