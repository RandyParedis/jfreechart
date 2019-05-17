#!/usr/bin/ruby
#
# Replaces all tabs in files found recursively in the given directories by the
# given number of spaces. Hidden directories and files are ignored. Also removes
# form feed bytes and ensures a new line character at the end of each file.
#
# WARNING: This rewrites the original files. Use at your own risk.
#
#   Software Engineering Group
#   University of Bremen
#
# Feel free to adjust to your own needs.
#

require "find"

# Check for enough parameters
unless ARGV.length >= 2
    puts "Usage: iclean <count> <dirs...>"
    puts "    count - Number of spaces to insert for each tab"
    puts "    dirs  - Directories in which files should be rewritten"
    exit
end

# Construct replacement string
replacement = ""
ARGV[0].to_i.times do replacement = replacement + " " end

# Iterate over all input directories
for i in 1 .. ARGV.length - 1 do
    dir = ARGV[i]
    
    # Recursively process all files in the current directory
    Find.find(dir) do |f|
        b = File.basename(f)
        
        # Skip hidden files and directories
        Find.prune if b.length > 1 && b[0 .. 0] == "."
        
        # Rewrite file
        if FileTest.file?(f) then
            lines = IO.readlines(f)
            out = File.open(f, "w")
            last = nil
            lines.each do |l|
                out.puts((l.gsub("\r", "").gsub("\t", replacement)))
                last = l
            end
            if last == nil || last.strip.length > 0 then
                out.puts ""
            end
            out.close
        end
    end
end

